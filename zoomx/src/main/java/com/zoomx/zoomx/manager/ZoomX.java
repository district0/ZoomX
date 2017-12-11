package com.zoomx.zoomx.manager;

import android.support.annotation.NonNull;

import com.zoomx.zoomx.db.AppDatabase;
import com.zoomx.zoomx.db.RequestDao;

/**
 * Created by Ahmed Fathallah on 12/10/2017.
 */

public class ZoomX {

    private static Config config;
    private static AppDatabase database;
    private static RequestDao requestDao;

    public static void init(Config config) {
        ZoomX.config = config;
        setupDataBase();
    }

    private static void setupDataBase() {
        database = AppDatabase.get(config.getContext());
        requestDao = database.requestDao();
    }


    @NonNull
    public static RequestDao getRequestDao() {
        return requestDao;
    }
}
