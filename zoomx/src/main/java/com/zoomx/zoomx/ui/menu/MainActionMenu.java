package com.zoomx.zoomx.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.ui.request.RequestActivity;

/**
 * Created by Ahmed Fathallah on 12/13/2017.
 */

public class MainActionMenu extends FrameLayout implements View.OnClickListener {

    private ImageView menuButton;
    private View expandedView;

    private boolean isMenuOpened = false;

    public MainActionMenu(@NonNull Context context) {
        super(context);
        initUI(context);
    }

    private void initUI(Context context) {
        View view = inflate(context, R.layout.view_main_actions_menu, this);
        menuButton = view.findViewById(R.id.menu_main_fab);
        ImageView dismissButton = view.findViewById(R.id.menu_dismiss_fab);
        ImageView settingsButton = view.findViewById(R.id.menu_settings_fab);
        ImageView featuresButton = view.findViewById(R.id.menu_features_fab);
        expandedView = view.findViewById(R.id.menu_expanded_view);

        menuButton.setOnClickListener(this);
        dismissButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        featuresButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.menu_main_fab) {
            expand();
        } else if (id == R.id.menu_dismiss_fab) {
            collapse();
            Toast.makeText(getContext(), "dismiss", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_settings_fab) {
            Toast.makeText(getContext(), "settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_features_fab) {
            Intent intent = new Intent(getContext(), RequestActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
            Toast.makeText(getContext(), "features", Toast.LENGTH_SHORT).show();
        }
    }

    public void collapse() {
        expandedView.setVisibility(GONE);
        menuButton.setVisibility(VISIBLE);
        isMenuOpened = false;
    }

    public void expand() {
        expandedView.setVisibility(VISIBLE);
        menuButton.setVisibility(GONE);
        isMenuOpened = true;
    }

    public boolean isMenuOpened() {
        return isMenuOpened;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
