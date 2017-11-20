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
