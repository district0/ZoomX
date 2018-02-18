package com.zoomx.zoomx.ui.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.zoomx.zoomx.R;

/**
 * Created by Ahmed Fathallah on 1/28/2018.
 */

public class MenuCloseView extends FrameLayout {

    public MenuCloseView(@NonNull Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        View view = inflate(getContext(), R.layout.view_menu_close, this);
    }
}
