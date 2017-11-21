package com.zoomx.zoomx.model;

import java.util.ArrayList;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

public class RequestModel {

    private String method;
    private long startDate;
    private String url;
    private ArrayList<HeaderViewModel> headers;
    private ArrayList<HeaderViewModel> responseHeaders;
    private String responseBody;
    private String requestBody;
    private long tookTime;
    private long responseSizeInBytes;
    private int code;
    private String title;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public ArrayList<HeaderViewModel> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(ArrayList<HeaderViewModel> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public long getTookTime() {
        return tookTime;
    }

    public void setTookTime(long tookTime) {
        this.tookTime = tookTime;
    }

    public long getResponseSizeInBytes() {
        return responseSizeInBytes;
    }

    public void setResponseSizeInBytes(long responseSizeInBytes) {
        this.responseSizeInBytes = responseSizeInBytes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<HeaderViewModel> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<HeaderViewModel> headers) {
        this.headers = headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
