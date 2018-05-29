package com.zoomx.zoomx.db.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

object DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
