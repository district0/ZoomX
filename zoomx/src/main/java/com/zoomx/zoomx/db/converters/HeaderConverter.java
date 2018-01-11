package com.zoomx.zoomx.db.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.zoomx.zoomx.model.HeaderViewModel;

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

public class HeaderConverter {
    @TypeConverter
    public static String toString(HeaderViewModel headersModel) {
        return new Gson().toJson(headersModel);
    }

    @TypeConverter
    public static HeaderViewModel toModel(String headers) {
        return new Gson().fromJson(headers, HeaderViewModel.class);
    }
}
