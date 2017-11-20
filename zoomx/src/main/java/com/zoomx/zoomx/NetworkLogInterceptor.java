package com.zoomx.zoomx;

import com.zoomx.zoomx.model.RequestModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ahmed Fathallah on 11/19/2017.
 */

public class NetworkLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        RequestModel request = new RequestModel();

        Request retrofitRequest = chain.request();
        RequestBody requestBody = retrofitRequest.body();


        Response response;
        try {
            response = chain.proceed(retrofitRequest);
        } catch (Exception e) {
            throw e;
        }

        return response;
    }

}
