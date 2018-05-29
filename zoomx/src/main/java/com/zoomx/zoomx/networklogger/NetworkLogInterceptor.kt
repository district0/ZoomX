package com.zoomx.zoomx.networklogger

import android.content.Context
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.ui.settings.SettingsManager
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * Created by Ahmed Fathallah on 11/19/2017.
 */

class NetworkLogInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val requestBuilder = RequestEntity.Builder()
        var response: Response? = null

        if (!SettingsManager.get(context).isNetworkTrackingEnabled) {
            return chain.proceed(chain.request())
        }
        val retrofitRequest = chain.request()
        val requestBody = retrofitRequest.body()

        val hasRequestBody = requestBody != null

        requestBuilder.setMethod(retrofitRequest.method())
                .setUrl(retrofitRequest.url().toString())
                .setRequestBody(if (hasRequestBody) requestBody!!.toString() else "")
                .setRequestHeaders(getHeaders(retrofitRequest.headers()))

        val startDateInMs = System.currentTimeMillis()
        try {
            response = chain.proceed(retrofitRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val timeTookInMs = System.currentTimeMillis() - startDateInMs
        requestBuilder.setStartDate(Date(startDateInMs))
        requestBuilder.setTookTime(timeTookInMs)

        if (response != null) {
            requestBuilder.setResponseHeaders(getHeaders(response.headers()))
            requestBuilder.setCode(response.code())
            requestBuilder.setResponseBody(responseBody(response))
        }

        NetworkLogManager.log(requestBuilder)
        return response
    }

    private fun getHeaders(headers: Headers?): MutableMap<String, String> {
        val headersMap = HashMap<String, String>()
        if (headers != null) {
            for (i in 0 until headers.size()) {
                headersMap[headers.name(i)] = headers.value(i)
            }
        }
        return headersMap
    }

    @Throws(IOException::class)
    private fun responseBody(response: Response): String {
        val responseBody = response.body()
        var body = ""
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()

            var charset: Charset? = UTF_8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF_8)
            }
            body = buffer.clone().readString(charset!!)
        }
        return body
    }

    companion object {

        private val UTF_8 = Charset.forName("utf-8")
    }
}
