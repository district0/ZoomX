package com.zoomx.zoomx.model

import java.util.*

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */
class HeaderViewModel(var headersMap: MutableMap<String, String> = HashMap()) {

    fun addHeader(name: String, value: String) {
        headersMap[name] = value
    }

    override fun toString(): String {
        return headersMap.map { it.value }.reduce { sum, element -> sum + "\n" + element }
    }

}
