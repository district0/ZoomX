package com.zoomx.zoomx.config;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zoomx.zoomx.db.AppDatabase;
import com.zoomx.zoomx.db.RequestDao;
import com.zoomx.zoomx.ui.menu.ZoomxMenuService;
import com.zoomx.zoomx.util.ShakeEventManager;

/**
 * Created by Ahmed Fathallah on 12/10/2017.
 */

public final class ZoomX {

    private static final String TAG = "ZoomX:Manager";

    @SuppressLint("StaticFieldLeak")
    private static Config config;
    private static RequestDao requestDao;
    private static ShakeEventManager mShakeEventManager;

    public static void init(Config config) {
        ZoomX.config = config;
        checkPreConditions();
        setupDataBase();
        handleShowMenuOnStart();
        handleShowMenuOnShakeEvent();
    }

    private static void checkPreConditions() {
        if (config.canShowOnShakeEvent() && config.canShowMenuOnAppStart()) {
            Log.d(TAG, "You should only enable show On shake or on app start");
        }
    }

    private static void handleShowMenuOnStart() {
        if (config.canShowMenuOnAppStart() && !config.canShowOnShakeEvent()) {
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

    public static void handleShowMenuOnShakeEvent() {
        Log.d(TAG, "Show Menu on shake called");
        if (config.canShowOnShakeEvent() && !config.canShowMenuOnAppStart()) {
            Log.d(TAG, "Show Menu on shake executed");
            mShakeEventManager = new ShakeEventManager(config.getContext());
            mShakeEventManager.listen(new ShakeEventManager.OnShakeEventListener() {
                @Override
                public void OnShakeDetected() {
                    Log.d(TAG, "shake detected");
                    showMenuHead();
                }
            });
        }
    }



    @NonNull
    public static RequestDao getRequestDao() {
        return requestDao;
    }
}
