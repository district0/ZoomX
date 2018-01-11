package com.zoomx.zoomx.db.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
