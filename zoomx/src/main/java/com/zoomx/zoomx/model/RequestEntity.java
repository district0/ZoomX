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
}
