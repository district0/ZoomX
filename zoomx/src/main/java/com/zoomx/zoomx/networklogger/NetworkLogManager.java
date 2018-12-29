package com.zoomx.zoomx.networklogger;

import android.os.AsyncTask;

import com.zoomx.zoomx.config.ZoomX;
import com.zoomx.zoomx.model.RequestEntity;

/**
 * Created by Ahmed Fathallah on 1/11/2018.
 */

public class NetworkLogManager {

    public static void log(RequestEntity.Builder builder) {
        if (builder != null)
            new DbAsyncTask().execute(builder);
    }

    public static class DbAsyncTask extends AsyncTask<RequestEntity.Builder, Void, Void> {
        @Override
        protected Void doInBackground(RequestEntity.Builder... builders) {
            ZoomX.getRequestDao().insertRequest(builders[0].create());
            return null;
        }
    }
}
