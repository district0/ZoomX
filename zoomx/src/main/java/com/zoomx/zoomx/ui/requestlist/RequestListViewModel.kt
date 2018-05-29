package com.zoomx.zoomx.ui.requestlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask

import com.zoomx.zoomx.config.ZoomX
import com.zoomx.zoomx.model.RequestEntity

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

class RequestListViewModel : ViewModel() {

    val requests: LiveData<List<RequestEntity>>

    init {
        requests = ZoomX.requestDao!!.loadRequests()
    }

    fun clearRequestFromDB(): DbAsyncTask {
        return DbAsyncTask()
    }

    class DbAsyncTask : AsyncTask<Void, Void, Int>() {
        override fun doInBackground(vararg voids: Void): Int? {
            return ZoomX.requestDao!!.clearRequestsData()
        }
    }
}
