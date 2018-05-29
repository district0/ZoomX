package com.zoomx.zoomx.util

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat

import com.zoomx.zoomx.R

/**
 * Created by Ibrahim AbdelGawad on 12/12/2017.
 */

object ColorUtils {

    fun getCodeColor(code: Int, context: Context): Int {

        val color: Int

        when (code) {
            200 -> color = ContextCompat.getColor(context, R.color.green_500)

            else -> color = Color.RED
        }

        return color
    }
}
