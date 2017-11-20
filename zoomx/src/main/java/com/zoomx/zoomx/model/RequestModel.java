package com.zoomx.zoomx.model;

import java.util.ArrayList;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

public class RequestModel {

    private String method;
    private long startDate;
    private String url;
    private ArrayList<String> headers;
    private ArrayList<String> bodyHeaders;
    private String body;
    private long tookTime;
    private long responseSizeInBytes;
    private String status;
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

    public ArrayList<String> getBodyHeaders() {
        return bodyHeaders;
    }

    public void setBodyHeaders(ArrayList<String> bodyHeaders) {
        this.bodyHeaders = bodyHeaders;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
