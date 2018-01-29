package com.zoomx.zoomx.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

public class Config {

    private Context context;
    private boolean showMenuOnAppStart;

    Config(@NonNull Builder builder) {
        this.context = builder.context;
    }

    public boolean canShowMenuOnAppStart() {
        return showMenuOnAppStart;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public static class Builder {
        Context context;
        boolean showMenuOnAppStart;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder showMenuOnAppStart(boolean showMenuOnAppStart) {
            this.showMenuOnAppStart = showMenuOnAppStart;
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
