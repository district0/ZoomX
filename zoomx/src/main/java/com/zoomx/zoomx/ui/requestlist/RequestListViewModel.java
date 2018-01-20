package com.zoomx.zoomx.ui.requestlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.zoomx.zoomx.config.ZoomX;
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

    public DbAsyncTask clearRequestFromDB() {
        return new DbAsyncTask();
    }

    public static class DbAsyncTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return ZoomX.getRequestDao().clearRequestsData();
        }
    }
}
