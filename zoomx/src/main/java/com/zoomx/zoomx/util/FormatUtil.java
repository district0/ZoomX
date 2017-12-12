package com.zoomx.zoomx.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

public class FormatUtil {

    final public static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    public static String formatDate(Date date, String format) {
        String formattedString = null;
        if (date != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                formattedString = simpleDateFormat.format(date);
            } catch (Exception e) {
                return "";
            }
        }
        return formattedString;
    }

}
