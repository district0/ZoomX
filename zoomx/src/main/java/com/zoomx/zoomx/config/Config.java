package com.zoomx.zoomx.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ahmed Fathallah on 12/11/2017.
 */

public class Config {

    private Context context;
    private boolean showMenuOnAppStart;
    private boolean showOnShakeEvent;

    Config(@NonNull Builder builder) {
        this.context = builder.context;
        this.showMenuOnAppStart = builder.showMenuOnAppStart;
        this.showOnShakeEvent = builder.showOnShakeEvent;
    }

    boolean canShowMenuOnAppStart() {
        return showMenuOnAppStart;
    }

    boolean canShowOnShakeEvent() {
        return showOnShakeEvent;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public static class Builder {
        Context context;
        boolean showMenuOnAppStart;
        boolean showOnShakeEvent;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder showMenuOnAppStart(boolean showMenuOnAppStart) {
            this.showMenuOnAppStart = showMenuOnAppStart;
            return this;
        }

        public Builder showOnShakeEvent(boolean showOnShakeEvent) {
            this.showOnShakeEvent = showOnShakeEvent;
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
