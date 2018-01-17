package com.zoomx.zoomx.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

public class Config {

    private Context context;


    Config(@NonNull Builder builder) {
        this.context = builder.context;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public static class Builder {
        Context context;
        int networkLayerType;

        public Builder(Context context) {
            this.context = context;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
