package com.zoomx.zoomx.ui.requestlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.zoomx.zoomx.config.ZoomX;
import com.zoomx.zoomx.model.RequestEntity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.Callable;

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

    public Completable clearRequestFromDB() {
        return Completable
                .fromCallable(ZoomX.getRequestDao()::clearRequestsData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
