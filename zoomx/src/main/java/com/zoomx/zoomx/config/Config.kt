package com.zoomx.zoomx.config

import android.content.Context
import com.zoomx.zoomx.ui.ZoomxUIOption

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

class Config internal constructor(builder: Builder) {

    val context: Context
    private val showMenuOnAppStart: Boolean
    private val showOnShakeEvent: Boolean
    val zoomxUIOption: Int

    init {
        this.context = builder.context
        this.showMenuOnAppStart = builder.showMenuOnAppStart
        this.showOnShakeEvent = builder.showOnShakeEvent
        this.zoomxUIOption = builder.zoomxUiOption
    }

    fun canShowMenuOnAppStart(): Boolean {
        return showMenuOnAppStart
    }

    fun canShowOnShakeEvent(): Boolean {
        return showOnShakeEvent
    }

    class Builder(internal var context: Context) {
        internal var showMenuOnAppStart: Boolean = false
        internal var showOnShakeEvent: Boolean = false
        internal var zoomxUiOption: Int = ZoomxUIOption.DRAW_OVER_APPS

        fun showMenuOnAppStart(showMenuOnAppStart: Boolean): Builder {
            this.showMenuOnAppStart = showMenuOnAppStart
            return this
        }

        fun showOnShakeEvent(showOnShakeEvent: Boolean): Builder {
            this.showOnShakeEvent = showOnShakeEvent
            return this
        }

        fun setZoomxUIOptions(@ZoomxUIOption option: Int): Builder {
            this.zoomxUiOption = option
            return this
        }

        fun build(): Config {
            return Config(this)
        }
    }
}
