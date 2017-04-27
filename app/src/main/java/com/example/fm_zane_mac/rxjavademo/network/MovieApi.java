package com.example.fm_zane_mac.rxjavademo.network;

import com.example.fm_zane_mac.rxjavademo.model.HttpResult;
import com.example.fm_zane_mac.rxjavademo.model.Subject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhouzhan on 25/10/16.
 */
public interface MovieApi {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
