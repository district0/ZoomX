package com.zoomx.zoomx.model;

import android.arch.persistence.room.Entity;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */
@Entity(tableName = "Headers")
public class HeaderViewModel {


    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
