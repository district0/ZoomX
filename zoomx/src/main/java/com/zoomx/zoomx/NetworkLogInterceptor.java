package com.zoomx.zoomx;

import com.zoomx.zoomx.model.HeaderViewModel;
import com.zoomx.zoomx.model.RequestModel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

    @Override
    public Response intercept(Chain chain) throws IOException {
        RequestModel request = new RequestModel();

        Request retrofitRequest = chain.request();
        RequestBody requestBody = retrofitRequest.body();

        boolean hasRequestBody = requestBody != null;

        request.setMethod(retrofitRequest.method());
        request.setUrl(retrofitRequest.url().toString());
        request.setRequestBody(hasRequestBody ? requestBody.toString() : "");
        request.setHeaders(getHeaders(retrofitRequest.headers()));

        long startNs = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(retrofitRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        request.setStartDate(startNs);
        request.setTookTime(tookMs);
        if (response != null) {
            request.setResponseHeaders(getHeaders(response.headers()));
            request.setCode(response.code());
            request.setResponseBody(responseBody(response));
        }

        return response;
    }

    private ArrayList<HeaderViewModel> getHeaders(Headers headers) {
        ArrayList<HeaderViewModel> responseViewHeaders = null;
        if (headers != null) {
            responseViewHeaders = new ArrayList<>();
            for (int i = 0; i < headers.size(); i++) {
                HeaderViewModel model = new HeaderViewModel();
                model.setName(headers.name(i));
                model.setHeader(headers.value(i));
                responseViewHeaders.add(model);
            }
        }
        return responseViewHeaders;
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
