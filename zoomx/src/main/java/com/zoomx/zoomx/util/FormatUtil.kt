package com.zoomx.zoomx.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

object FormatUtil {

    const val DATE_FORMAT = "yyyy-MM-dd hh:mm:ss a"

    fun formatDate(date: Date?, format: String): String {
        var formattedString = ""
        if (date != null) {
            try {
                val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
                formattedString = simpleDateFormat.format(date)
            } catch (e: Exception) {
                return ""
            }

        }
        return formattedString
    }

}
