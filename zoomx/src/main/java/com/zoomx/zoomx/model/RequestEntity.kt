package com.zoomx.zoomx.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.zoomx.zoomx.db.converters.HeaderConverter
import java.util.*

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */

@Entity(tableName = "requests")
class RequestEntity {
    //region Setter and getter
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var code: Int = 0
    var method: String? = null
        get() = if (field != null) field else ""
    var startDate: Date? = null
    var url: String? = null
        get() = if (field != null) field else "missing"
    var responseBody: String? = null
        get() = if (field != null) field else ""
    var requestBody: String? = null
        get() = if (field != null) field else ""
    var tookTime: Long = 0
    var responseSizeInBytes: Long = 0

    @TypeConverters(HeaderConverter::class)
    var requestHeaders: HeaderViewModel? = null
    @TypeConverters(HeaderConverter::class)
    var responseHeaders: HeaderViewModel? = null

    constructor() {}

    private constructor(code: Int, method: String?, startDate: Date?, url: String?, responseBody: String?, requestBody: String?, tookTime: Long, responseSizeInBytes: Long, requestHeaders: HeaderViewModel, responseHeaders: HeaderViewModel) {
        this.code = code
        this.method = method
        this.startDate = startDate
        this.url = url
        this.responseBody = responseBody
        this.requestBody = requestBody
        this.tookTime = tookTime
        this.responseSizeInBytes = responseSizeInBytes
        this.requestHeaders = requestHeaders
        this.responseHeaders = responseHeaders
    }

    //endregion

    //region builder
    class Builder {

        private var code: Int = 0
        private var method: String? = null
        private var startDate: Date? = null
        private var url: String? = null
        private var responseBody: String? = null
        private var requestBody: String? = null
        private var tookTime: Long = 0
        private var responseSizeInBytes: Long = 0
        private var requestHeaders: MutableMap<String, String>? = null
        private var responseHeaders: MutableMap<String, String>? = null

        fun setCode(code: Int): Builder {
            this.code = code
            return this
        }

        fun setMethod(method: String): Builder {
            this.method = method
            return this
        }

        fun setStartDate(startDate: Date): Builder {
            this.startDate = startDate
            return this
        }

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun setResponseBody(responseBody: String): Builder {
            this.responseBody = responseBody
            return this
        }

        fun setRequestBody(requestBody: String): Builder {
            this.requestBody = requestBody
            return this
        }

        fun setTookTime(tookTime: Long): Builder {
            this.tookTime = tookTime
            return this
        }

        fun setResponseSizeInBytes(responseSizeInBytes: Long): Builder {
            this.responseSizeInBytes = responseSizeInBytes
            return this
        }

        fun setRequestHeaders(requestHeaders: MutableMap<String, String>): Builder {
            this.requestHeaders = requestHeaders
            return this
        }

        fun setResponseHeaders(responseHeaders: MutableMap<String, String>): Builder {
            this.responseHeaders = responseHeaders
            return this
        }

        fun create(): RequestEntity {
            val requestHeadersModel = HeaderViewModel(requestHeaders!!)
            val responseHeadersModel = HeaderViewModel(responseHeaders!!)

            return RequestEntity(code, method, startDate, url, responseBody, requestBody, tookTime, responseSizeInBytes, requestHeadersModel, responseHeadersModel)
        }
    }
    //endregion

}
