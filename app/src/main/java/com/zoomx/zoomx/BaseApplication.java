package com.zoomx.zoomx;

import android.app.Application;

import com.zoomx.zoomx.manager.Config;
import com.zoomx.zoomx.manager.ZoomX;

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZoomX.init(new Config.Builder(this)
                .build());
    }
}
