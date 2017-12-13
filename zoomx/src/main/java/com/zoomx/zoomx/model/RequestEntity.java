package com.zoomx.zoomx.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.zoomx.zoomx.db.converters.HeaderConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

@Entity(tableName = "requests")
public class RequestEntity implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private int code;
    private String method;
    private Date startDate;
    private String url;
    private String responseBody;
    private String requestBody;
    private long tookTime;
    private long responseSizeInBytes;

    @TypeConverters(HeaderConverter.class)
    private HeaderViewModel rquestHeaders;
    @TypeConverters(HeaderConverter.class)
    private HeaderViewModel responseHeaders;

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

    public HeaderViewModel getRquestHeaders() {
        return rquestHeaders;
    }

    public void setRquestHeaders(HeaderViewModel rquestHeaders) {
        this.rquestHeaders = rquestHeaders;
    }

    public HeaderViewModel getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HeaderViewModel responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
