package com.zoomx.zoomx.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.zoomx.zoomx.manager.ZoomX;
import com.zoomx.zoomx.model.RequestEntity;

import java.util.List;

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

public class RequestListViewModel extends ViewModel {

    private LiveData<List<RequestEntity>> mRequests;

    public RequestListViewModel() {
        mRequests = ZoomX.getRequestDao().loadRequests();
    }

    public LiveData<List<RequestEntity>> getRequests() {
        return mRequests;
    }
}
