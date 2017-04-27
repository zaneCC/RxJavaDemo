package com.example.fm_zane_mac.rxjavademo.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangliu on 16/9/28.
 */
public class ApiManager {

    private static ApiManager apiManager;
    private static MovieApi configureApi;
    /**
     * Gets instence.
     *
     * @return the instence
     */
    public static ApiManager getInstence() {
        if (null == apiManager) {
            synchronized (ApiManager.class) {
                if (null == apiManager) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    private Retrofit buildApi(String url) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    /**
     * Gets configure api service.
     *
     * @return the configure api service
     */
    public MovieApi getConfigureApiService() {
        if (null == configureApi) {
            synchronized (ApiManager.class) {
                if (null == configureApi) {
                    configureApi = buildApi(Api.CONFIG_API_BASE_URL)
                            .create(MovieApi.class);
                }
            }
        }
        return configureApi;
    }
}
