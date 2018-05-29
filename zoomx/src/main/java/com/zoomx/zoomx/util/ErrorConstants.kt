package com.zoomx.zoomx.util

import android.support.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Ibrahim AbdelGawad on 1/11/2018.
 */

object ErrorConstants {

    const val CONNECTION_ERROR = 0

    @IntDef(CONNECTION_ERROR.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class Errors
}
