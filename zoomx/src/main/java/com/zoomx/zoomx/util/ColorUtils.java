package com.zoomx.zoomx.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.zoomx.zoomx.R;

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

public class ColorUtils {

    public static int getCodeColor(int code, Context context) {

        int color;

        switch (code) {
            case 200:
                color = ContextCompat.getColor(context, R.color.green_500);
                break;

            default:
                color = Color.RED;
                break;
        }

        return color;
    }
}
