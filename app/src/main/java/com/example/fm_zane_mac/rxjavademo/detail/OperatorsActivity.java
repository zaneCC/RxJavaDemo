package com.example.fm_zane_mac.rxjavademo.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.fm_zane_mac.rxjavademo.BaseActivity;
import com.example.fm_zane_mac.rxjavademo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 常用操作符
 * <p>
 * Created by fm_zane_mac on 2017/4/27.
 */
public class OperatorsActivity extends BaseActivity {

    private static final String TITLE = "常用操作符";
    private CompositeDisposable disposablePool = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
        setTitle(TITLE);
    }

    public void doClean(View view){
        disposablePool.clear();
    }

    /**
     * 变换操作
     */
    public void doTransform(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.map:
                doMap();
                break;
            case R.id.flatMap:
                doFlatMap();
                break;
            case R.id.concatMap:
                doConcatMap();
                break;
        }
    }

    /**
     * 过滤操作
     */
    public void doFilter(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.filter:
                doFilter();
                break;
            case R.id.simple:
                doSimple();
                break;
        }
    }

    /**
     * 结合操作
     */
    public void doCombine(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.zip:
                doZip();
                break;
        }
    }

    /**
     * 辅助操作
     */
    public void doAuxiliary(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.timeout:
                doTimeout();
                break;
            case R.id.delay:
                doDelay();
                break;
        }
    }

    /**
     * 创建操作
     */
    public void doCreate(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.interval:
                doInterval();
                break;
        }
    }

    /************************* map *************************/
    /**
     * 变换
     */
    private void doMap() {
        final String TAG = "doMap";

        getObservableInt()
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer value) throws Exception {
                        return "str " + value;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, s);
                    }
                });
    }

    /**
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，
     * 然后将它们发射的事件合并后放进一个单独的Observable里.
     * <p>
     * 无序的
     */
    private void doFlatMap() {
        final String TAG = "doFlatMap";

        /** Integer */
        getObservableInt()
                /** 变换 */
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("I am value " + integer);
                        }
                        return Observable.fromIterable(list)
                                /** 这里进行延迟 判断一下循序 */
                                .delay(10, TimeUnit.MILLISECONDS);
                    }
                })
                /** String */
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, s);
                    }
                });

    }

    /**
     * 跟 FlatMap 一样， 不过 他是有序的
     */
    private void doConcatMap() {
        final String TAG = "doFlatMap";

        /** Integer */
        getObservableInt()
                /** 变换 */
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("I am value " + integer);
                        }
                        return Observable.fromIterable(list)
                                /** 这里进行延迟 判断一下循序 */
                                .delay(10, TimeUnit.MILLISECONDS);
                    }
                })
                /** String */
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, s);
                    }
                });

    }

    /**
     * 将多个 observable 打包成 一个新的 observable 处理
     */
    private void doZip() {
        final String TAG = "doZip";

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "emit 1");
                emitter.onNext(1);
                Log.e(TAG, "emit 2");
                emitter.onNext(2);
                Log.e(TAG, "emit 3");
                emitter.onNext(3);
                Log.e(TAG, "emit 4");
                emitter.onNext(4);
                Log.e(TAG, "emit complete1");
                emitter.onComplete();
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "emit A");
                emitter.onNext("A");
                Log.e(TAG, "emit B");
                emitter.onNext("B");
                Log.e(TAG, "emit C");
                emitter.onNext("C");
                Log.e(TAG, "emit complete2");
                emitter.onComplete();
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }

    /**
     * sample操作符，这个操作符每隔指定的时间就从上游中取出一个事件发送给下游
     */
    private void doSimple() {
        final String TAG = "doSimple";
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)  //sample取样
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "" + integer);
                    }
                });
    }

    /**
     * 简简单单的过滤，不解释
     */
    private void doFilter() {
        final String TAG = "doFilter";

        getObservableInt()
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer value) throws Exception {
                        return value > 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer value) throws Exception {
                        Log.e(TAG, "" + value);
                    }
                });
    }

    /**
     * 如果过了一个指定的时长仍没有发射数据，它会发一个错误通知
     */
    private void doTimeout() {
        final String TAG = "doTimeout";
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .timeout(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e(TAG, "" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "过了两秒 超时 报错啦啦啦");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void doDelay() {
        final String TAG = "doDelay";
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "" + integer);
                    }
                });
    }

    private void doInterval() {
        final String TAG = "doInterval";

        long interval = 3;
        Observable.interval(0, interval, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnDispose");
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposablePool.add(d);
                    }

                    @Override
                    public void onNext(Long value) {
                        value++;
                        Log.e(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

    }

    private Observable<Integer> getObservableInt() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    emitter.onNext(i);
                }
            }
        });
    }

}
