package com.zoomx.zoomx.ui.menu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Ahmed Fathallah on 12/14/2017.
 */

public class ZoomxMenuService extends Service implements MainActionMenu.ActionMenuEventsListener {

    private static final String MENU_STATE_KEY = "menuActionState";
    private static final String SHOW_MENU_KEY = "showMenu";
    private static final String HIDE_MENU_KEY = "hideMenu";

    private MainActionMenu menuHeadLayout;
    private MenuCloseView menuCloseView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams menuParams;

    public static void showMenuHead(Context context) {
        Intent intent = new Intent(context, ZoomxMenuService.class);
        Bundle bundle = new Bundle();
        bundle.putString(MENU_STATE_KEY, SHOW_MENU_KEY);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    public static void hideMenuHead(Context context) {
        Intent intent = new Intent(context, ZoomxMenuService.class);
        Bundle bundle = new Bundle();
        bundle.putString(MENU_STATE_KEY, HIDE_MENU_KEY);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        menuHeadLayout = new MainActionMenu(this, this);
        menuCloseView = new MenuCloseView(this);
        menuParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getWindowOverlayFlag(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        menuParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        menuParams.x = 0;
        menuParams.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    private int getWindowOverlayFlag() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else
            return WindowManager.LayoutParams.TYPE_PHONE;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getExtras() != null) {
            if (SHOW_MENU_KEY.equals(intent.getExtras().getString(MENU_STATE_KEY))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myIntent);
                } else {
                    mWindowManager.addView(menuHeadLayout, menuParams);
                    //addCloseView();
                }
            } else if (HIDE_MENU_KEY.equals(intent.getExtras().getString(MENU_STATE_KEY))) {
                hideMenu();
            }
        }

        return START_NOT_STICKY;
    }

    private void hideMenu() {
        try {
            if (this.menuHeadLayout != null && menuHeadLayout.getWindowToken() != null) {
                this.mWindowManager.removeView(this.menuHeadLayout);
                this.menuHeadLayout = null;
                this.stopSelf();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

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
        menuCloseView.setVisibility(View.VISIBLE);
        menuParams.x = (int) (menuParams.x - dx);
        menuParams.y = (int) (menuParams.y + dy);
        mWindowManager.updateViewLayout(menuHeadLayout, menuParams);
    }

    private void addCloseView() {
        mWindowManager.addView(menuCloseView, buildLayoutParamsForClose());
    }

    private WindowManager.LayoutParams buildLayoutParamsForClose() {
        int x = 0;
        int y = 0;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getWindowOverlayFlag(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        params.x = x;
        params.y = y;
        return params;
    }
}
