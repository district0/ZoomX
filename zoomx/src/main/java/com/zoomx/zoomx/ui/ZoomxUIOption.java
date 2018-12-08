package com.zoomx.zoomx.ui;

import android.support.annotation.IntDef;

/**
 * Created by Mohamed Ibrahim on 12/8/18.
 */
@IntDef({
        ZoomxUIOption.DRAW_OVER_APPS,
        ZoomxUIOption.NOTIFICATION
})
public @interface ZoomxUIOption {
    int NOTIFICATION = 0;
    int DRAW_OVER_APPS = 1;
}