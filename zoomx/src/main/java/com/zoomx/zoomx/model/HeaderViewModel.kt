package com.zoomx.zoomx.model

import java.util.*

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */
class HeaderViewModel {

    private var headersMap: MutableMap<String, String>? = null

    constructor() {
        this.headersMap = HashMap()
    }

    constructor(headersMap: MutableMap<String, String>) {
        this.headersMap = headersMap
    }

    fun addHeader(name: String, value: String) {
        headersMap!![name] = value
    }

    fun getHeadersMap(): Map<String, String>? {
        return headersMap
    }
}
