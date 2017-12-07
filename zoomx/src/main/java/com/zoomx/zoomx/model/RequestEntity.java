package com.zoomx.zoomx.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

@Entity(tableName = "requests")
public class RequestEntity {
    @PrimaryKey
    private int id;
    private int code;
    private String method;
    private Date startDate;
    private String url;
    private String responseBody;
    private String requestBody;
    private long tookTime;
    private long responseSizeInBytes;

    private ArrayList<HeaderViewModel> headers;
    private ArrayList<HeaderViewModel> responseHeaders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public ArrayList<HeaderViewModel> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<HeaderViewModel> headers) {
        this.headers = headers;
    }

    public ArrayList<HeaderViewModel> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(ArrayList<HeaderViewModel> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
