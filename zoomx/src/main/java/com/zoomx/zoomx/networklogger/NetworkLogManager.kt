package com.zoomx.zoomx.networklogger

import android.os.AsyncTask

import com.zoomx.zoomx.config.ZoomX
import com.zoomx.zoomx.model.RequestEntity

/**
 * Created by Ahmed Fathallah on 1/11/2018.
 */

object NetworkLogManager {

    fun log(builder: RequestEntity.Builder?) {
        if (builder != null)
            DbAsyncTask().execute(builder)
    }

    class DbAsyncTask : AsyncTask<RequestEntity.Builder, Void, Void>() {
        override fun doInBackground(vararg builders: RequestEntity.Builder): Void? {
            ZoomX.requestDao!!.insertRequest(builders[0].create())
            return null
        }
    }
}
