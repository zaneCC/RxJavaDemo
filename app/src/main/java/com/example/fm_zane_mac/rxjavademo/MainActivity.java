package com.example.fm_zane_mac.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fm_zane_mac.rxjavademo.detail.LifecycleActivity;
import com.example.fm_zane_mac.rxjavademo.detail.OperatorsActivity;
import com.example.fm_zane_mac.rxjavademo.detail.TrilogyActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private MyAdapter mAdapter;

    private static final String[] PAGES = {"三部曲", "生命周期", "常用操作符"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);

        List<ContentItem> chartViews = new ArrayList<>();
        for (int i = 0; i < PAGES.length; i++) {
            chartViews.add(new ContentItem(PAGES[i], ""));
        }
        mAdapter = new MyAdapter(mContext, chartViews);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(mContext, TrilogyActivity.class));
                break;
            case 1:
                startActivity(new Intent(mContext, LifecycleActivity.class));
                break;
            case 2:
                startActivity(new Intent(mContext, OperatorsActivity.class));
                break;

        }
    }

}
