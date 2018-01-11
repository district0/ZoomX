package com.zoomx.zoomx.retrofit;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zoomx.zoomx.NetworkLogInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

public class NetworkManager {

    private Retrofit retrofit;
    private ApiService apiService;
    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
        initRetrofit();
    }

    private void initRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new NetworkLogInterceptor(this.context));

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public ApiService service() {
        if (apiService == null) {
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
