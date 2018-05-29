package com.zoomx.zoomx.db.converters

import android.arch.persistence.room.TypeConverter

import com.google.gson.Gson
import com.zoomx.zoomx.model.HeaderViewModel

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

object HeaderConverter {
    @TypeConverter
    fun toString(headersModel: HeaderViewModel): String {
        return Gson().toJson(headersModel)
    }

    @TypeConverter
    fun toModel(headers: String): HeaderViewModel {
        return Gson().fromJson(headers, HeaderViewModel::class.java)
    }
}
