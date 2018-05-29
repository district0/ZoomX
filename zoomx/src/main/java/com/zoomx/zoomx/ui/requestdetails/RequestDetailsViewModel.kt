package com.zoomx.zoomx.ui.requestdetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

import com.zoomx.zoomx.config.ZoomX
import com.zoomx.zoomx.model.RequestEntity

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */

class RequestDetailsViewModel : ViewModel() {

    fun getRequestById(id: Int): LiveData<RequestEntity> {
        return ZoomX.requestDao!!.getRequestById(id)
    }
}
