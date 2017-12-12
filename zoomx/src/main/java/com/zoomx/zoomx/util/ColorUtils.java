package com.zoomx.zoomx.util;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.zoomx.zoomx.model.RequestEntity;

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

public class ColorUtils {

    public static int getCodeColor(int code) {

        int color = Color.BLACK;

        switch (code) {
            case 200:
                color = Color.GREEN;
                break;

            default:
                color = Color.RED;
                break;
        }

        return color;
    }
}
