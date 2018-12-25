package com.zoomx.zoomx.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

public class FormatUtil {

    final public static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    public static String formatDate(Date date, String format) {
        String formattedString = "";
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

    public static String bodyToString(final RequestBody request){
        try {
            final Buffer buffer = new Buffer();
            if(request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

}
