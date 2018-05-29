package com.zoomx.zoomx.model

/**
 * Created by Ahmed Fathallah on 2/15/2018.
 */

class InfoModel {

    var title: String? = null
    var type: Int = 0
    var value: String? = null
    var isState: Boolean = false
    var buttonAction: Runnable? = null
        private set

    val isTextViewType: Boolean
        get() = TEXT_VIEW_TYPE == type

    val isSwitchType: Boolean
        get() = SWITCH_VIEW_TYPE == type

    val isButtonType: Boolean
        get() = BUTTON_VIEW_TYPE == type

    constructor(type: Int, title: String, value: String) {
        this.title = title
        this.type = type
        this.value = value
        this.buttonAction = null
    }

    constructor(type: Int, title: String, value: String, buttonAction: Runnable) {
        this.title = title
        this.type = type
        this.value = value
        this.buttonAction = buttonAction
    }

    companion object {
        val TEXT_VIEW_TYPE = 601
        val SWITCH_VIEW_TYPE = 602
        val BUTTON_VIEW_TYPE = 603
    }
}
