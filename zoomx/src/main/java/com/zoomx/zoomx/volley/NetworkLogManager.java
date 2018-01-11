package com.zoomx.zoomx.volley;

import com.zoomx.zoomx.manager.ZoomX;
import com.zoomx.zoomx.model.RequestEntity;

/**
 * Created by Ahmed Fathallah on 1/11/2018.
 */

public class NetworkLogManager {

    public static void log(RequestEntity.Builder builder) {
        if (builder != null)
            ZoomX.getRequestDao().insertRequest(builder.create());
    }
}
