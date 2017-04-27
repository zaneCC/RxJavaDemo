package com.example.fm_zane_mac.rxjavademo.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.fm_zane_mac.rxjavademo.BaseActivity;
import com.example.fm_zane_mac.rxjavademo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava 三部曲
 *
 * 1、什么是 Observable
 * 2、什么是 Observer
 * 3、什么是 Disposable
 *
 * Created by fm_zane_mac on 2017/4/25.
 */
public class TrilogyActivity extends BaseActivity {

    private static final String TITLE = "RxJava 三部曲";
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);

        setContentView(R.layout.activity_trilogy);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void doClick(View view){
        /** 1、创建一个 Observable（上游） */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("zane 你好帅！");
            }
        });

        /** 2、创建一个 Observer（下游） */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                textView.setText(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        /** 3、建立连接 */
        observable.subscribe(observer);
    }

}
