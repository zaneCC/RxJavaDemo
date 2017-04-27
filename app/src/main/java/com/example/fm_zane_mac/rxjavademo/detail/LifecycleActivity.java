package com.example.fm_zane_mac.rxjavademo.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fm_zane_mac.rxjavademo.BaseActivity;
import com.example.fm_zane_mac.rxjavademo.R;
import com.example.fm_zane_mac.rxjavademo.model.HttpResult;
import com.example.fm_zane_mac.rxjavademo.model.Subject;
import com.example.fm_zane_mac.rxjavademo.network.ApiManager;
import com.example.fm_zane_mac.rxjavademo.network.MovieApi;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 生命周期管理
 * <p>
 * Created by fm_zane_mac on 2017/4/27.
 */
public class LifecycleActivity extends BaseActivity {

    private static final String TAG = LifecycleActivity.class.getSimpleName();
    private static final String TITLE = "生命周期管理";

    private TextView textView;
    private ContentLoadingProgressBar progress;
    private CompositeDisposable disposablePool = new CompositeDisposable();
    private MovieApi movieApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);

        setContentView(R.layout.activity_lifecycle);
        textView = (TextView) findViewById(R.id.textView);
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);

        this.movieApi = ApiManager.getInstence().getConfigureApiService();
    }

    public void doClick(View view) {

        /** 获取豆瓣电影 top10 */
        movieApi.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程
                .subscribe(new Observer<HttpResult<List<Subject>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progress.setVisibility(View.VISIBLE);

                        disposablePool.add(d);
                        Log.e(TAG, "subscribe");
                    }

                    @Override
                    public void onNext(HttpResult<List<Subject>> value) {
                        progress.setVisibility(View.GONE);

                        textView.setText(value.toString());
                        Log.e(TAG, "" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "complete");
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposablePool.clear();
    }

}
