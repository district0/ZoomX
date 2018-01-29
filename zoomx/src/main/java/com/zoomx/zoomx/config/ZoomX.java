package com.zoomx.zoomx.config;

import android.annotation.SuppressLint;
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
        handleShowMenuOnStart();
    }

    private static void handleShowMenuOnStart() {
        if (config.canShowMenuOnAppStart()) {
            showMenuHead();
        }
    }

    private static void setupDataBase() {
        AppDatabase database = AppDatabase.get(config.getContext());
        requestDao = database.requestDao();
    }

    public static void showMenuHead() {
        ZoomxMenuService.showMenuHead(config.getContext());
    }

    public static void hideMenuHead() {
        ZoomxMenuService.hideMenuHead(config.getContext());
    }

    @NonNull
    public static RequestDao getRequestDao() {
        return requestDao;
    }


}
