package com.zoomx.zoomx.ui.menu;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Ahmed Fathallah on 12/14/2017.
 */

public class ZoomxMenuService extends Service implements View.OnTouchListener {

    private MainActionMenu menuHeadLayout;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams menuParams;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public void onCreate() {
        super.onCreate();
        menuHeadLayout = new MainActionMenu(this);
        menuHeadLayout.setOnTouchListener(this);
        menuParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        menuParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;        //Initially view will be added to top-left corner
        menuParams.x = 0;
        menuParams.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        } else {
            mWindowManager.addView(menuHeadLayout, menuParams);
        }
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
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = menuParams.x;
                initialY = menuParams.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;

            case MotionEvent.ACTION_UP:
                int Xdiff = (int) (event.getRawX() - initialTouchX);
                int Ydiff = (int) (event.getRawY() - initialTouchY);
                if (Xdiff < 5 && Ydiff < 5) {
                    menuHeadLayout.performClick();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                menuParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                menuParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                mWindowManager.updateViewLayout(menuHeadLayout, menuParams);
                return true;
        }
        return false;
    }

}
