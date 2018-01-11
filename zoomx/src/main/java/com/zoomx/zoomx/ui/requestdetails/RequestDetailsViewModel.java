package com.zoomx.zoomx.ui.requestdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.zoomx.zoomx.manager.ZoomX;
import com.zoomx.zoomx.model.RequestEntity;

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */

public class RequestDetailsViewModel extends ViewModel {

    public LiveData<RequestEntity> getRequestById(int id) {
        return ZoomX.getRequestDao().getRequestById(id);
    }
}
