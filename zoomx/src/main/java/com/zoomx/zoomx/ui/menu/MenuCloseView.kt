package com.zoomx.zoomx.ui.menu

import android.content.Context
import android.view.View
import android.widget.FrameLayout

import com.zoomx.zoomx.R

/**
 * Created by Ahmed Fathallah on 1/28/2018.
 */

class MenuCloseView(context: Context) : FrameLayout(context) {

    init {
        initUI()
    }

    private fun initUI() {
        val view = View.inflate(context, R.layout.view_menu_close, this)
    }
}
