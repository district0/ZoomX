package com.zoomx.zoomx.ui.requestlist.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zoomx.zoomx.R;

/**
 * Created by Ahmed Fathallah on 12/13/2017.
 */

public class MainActionMenu extends FrameLayout implements View.OnClickListener {

    private FloatingActionButton menuButton;
    private FloatingActionButton dismissButton;
    private FloatingActionButton settingsButton;
    private FloatingActionButton featuresButton;

    public MainActionMenu(@NonNull Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_main_actions_menu, null);
        addView(view);
        menuButton = findViewById(R.id.menu_main_fab);
        dismissButton = findViewById(R.id.menu_dismiss_fab);
        settingsButton = findViewById(R.id.menu_settings_fab);
        featuresButton = findViewById(R.id.menu_features_fab);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.menu_main_fab) {
            Toast.makeText(getContext(), "Main", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_dismiss_fab) {
            Toast.makeText(getContext(), "dismiss", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_settings_fab) {
            Toast.makeText(getContext(), "settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_features_fab) {
            Toast.makeText(getContext(), "features", Toast.LENGTH_SHORT).show();
        }
    }
}
