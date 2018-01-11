package com.zoomx.zoomx;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.ui.settings.SettingsManager;
import com.zoomx.zoomx.volley.NetworkLogManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Ahmed Fathallah on 11/19/2017.
 */

public class NetworkLogInterceptor implements Interceptor {

    private static final Charset UTF_8 = Charset.forName("utf-8");
    private Context context;

    public NetworkLogInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        RequestEntity.Builder requestBuilder = new RequestEntity.Builder();
        Response response = null;

        if (!SettingsManager.get(context).isNetworkTrackingEnabled()) {
            return chain.proceed(chain.request());
        }
        Request retrofitRequest = chain.request();
        RequestBody requestBody = retrofitRequest.body();

        boolean hasRequestBody = requestBody != null;

        requestBuilder.setMethod(retrofitRequest.method())
                .setUrl(retrofitRequest.url().toString())
                .setRequestBody(hasRequestBody ? requestBody.toString() : "")
                .setRequestHeaders(getHeaders(retrofitRequest.headers()));

        long startDateInMs = System.currentTimeMillis();
        try {
            response = chain.proceed(retrofitRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long timeTookInMs = System.currentTimeMillis() - startDateInMs;
        requestBuilder.setStartDate(new Date(startDateInMs));
        requestBuilder.setTookTime(timeTookInMs);

        if (response != null) {
            requestBuilder.setResponseHeaders(getHeaders(response.headers()));
            requestBuilder.setCode(response.code());
            requestBuilder.setResponseBody(responseBody(response));
        }

        NetworkLogManager.log(requestBuilder);
        return response;
    }

    private Map<String, String> getHeaders(Headers headers) {
        Map<String, String> headersMap = new HashMap<>();
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                headersMap.put(headers.name(i), headers.value(i));
            }
        }
        return headersMap;
    }

    private String responseBody(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        String body = "";
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }
            body = buffer.clone().readString(charset);
        }
        return body;
    }
}
