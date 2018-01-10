package com.zoomx.zoomx.ui.menu;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by Ahmed Fathallah on 12/14/2017.
 */

public class ZoomxMenuService extends Service implements MainActionMenu.ActionMenuEventsListener {

    private MainActionMenu menuHeadLayout;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams menuParams;


    @Override
    public void onCreate() {
        super.onCreate();
        menuHeadLayout = new MainActionMenu(this, this);
        menuParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getWindowOverlayFlag(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        menuParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;        //Initially view will be added to top-left corner
        menuParams.x = 0;
        menuParams.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        } else {
            mWindowManager.addView(menuHeadLayout, menuParams);
        }
    }

    private int getWindowOverlayFlag() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else
            return WindowManager.LayoutParams.TYPE_PHONE;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (menuHeadLayout != null) mWindowManager.removeView(menuHeadLayout);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void OnMenuMoved(float dx, float dy) {
        menuParams.x = (int) (menuParams.x + dx);
        menuParams.y = (int) (menuParams.y + dy);
        mWindowManager.updateViewLayout(menuHeadLayout, menuParams);
    }
}
