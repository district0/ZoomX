package com.zoomx.zoomx.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.zoomx.zoomx.db.converters.HeaderConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

@Entity(tableName = "requests")
public class RequestEntity {
    @PrimaryKey(autoGenerate = true)
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
    private HeaderViewModel requestHeaders;
    @TypeConverters(HeaderConverter.class)
    private HeaderViewModel responseHeaders;

    public RequestEntity() {
    }

    private RequestEntity(int code, String method, Date startDate, String url
            , String responseBody, String requestBody, long tookTime, long responseSizeInBytes
            , HeaderViewModel requestHeaders, HeaderViewModel responseHeaders) {
        this.code = code;
        this.method = method;
        this.startDate = startDate;
        this.url = url;
        this.responseBody = responseBody;
        this.requestBody = requestBody;
        this.tookTime = tookTime;
        this.responseSizeInBytes = responseSizeInBytes;
        this.requestHeaders = requestHeaders;
        this.responseHeaders = responseHeaders;
    }

    //region Setter and getter
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
        return method != null ? method : "";
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
        return url != null ? url : "missing";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseBody() {
        return responseBody != null ? responseBody : "";
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getRequestBody() {
        return requestBody != null ? requestBody : "";
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

    public HeaderViewModel getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(HeaderViewModel requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public HeaderViewModel getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HeaderViewModel responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    //endregion

    //region builder
    public static class Builder {

        private int code;
        private String method;
        private Date startDate;
        private String url;
        private String responseBody;
        private String requestBody;
        private long tookTime;
        private long responseSizeInBytes;
        private ArrayList<String> requestHeaders;
        private ArrayList<String> responseHeaders;

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setResponseBody(String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public Builder setRequestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder setTookTime(long tookTime) {
            this.tookTime = tookTime;
            return this;
        }

        public Builder setResponseSizeInBytes(long responseSizeInBytes) {
            this.responseSizeInBytes = responseSizeInBytes;
            return this;
        }

        public Builder setRequestHeaders(ArrayList<String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }

        public Builder setResponseHeaders(ArrayList<String> responseHeaders) {
            this.responseHeaders = responseHeaders;
            return this;
        }

        public RequestEntity create() {
            HeaderViewModel requestHeadersModel = new HeaderViewModel(requestHeaders);
            HeaderViewModel responseHeadersModel = new HeaderViewModel(responseHeaders);

            return new RequestEntity(code, method, startDate, url, responseBody, requestBody, tookTime
                    , responseSizeInBytes, requestHeadersModel, responseHeadersModel);
        }
    }
    //endregion

}
