package com.zoomx.zoomx.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.zoomx.zoomx.db.AppDatabase;
import com.zoomx.zoomx.db.RequestDao;
import com.zoomx.zoomx.ui.menu.ZoomxMenuService;

/**
 * Created by Ahmed Fathallah on 12/10/2017.
 */

public final class ZoomX {

    @SuppressLint("StaticFieldLeak")
    private static Config config;
    private static RequestDao requestDao;

    public static void init(Config config) {
        ZoomX.config = config;
        setupDataBase();
        config.getContext().startService(new Intent(config.getContext(), ZoomxMenuService.class));
    }

    private static void setupDataBase() {
        AppDatabase database = AppDatabase.get(config.getContext());
        requestDao = database.requestDao();
    }

    @NonNull
    public static RequestDao getRequestDao() {
        return requestDao;
    }
}
