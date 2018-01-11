package com.zoomx.zoomx.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.ui.request.RequestActivity;
import com.zoomx.zoomx.ui.settings.SettingActivity;

/**
 * Created by Ahmed Fathallah on 12/13/2017.
 */

public class MainActionMenu extends FrameLayout implements View.OnClickListener {

    boolean isScrolling = false;
    private ImageView menuButton;
    private View expandedView;
    private boolean isMenuOpened = false;
    private ActionMenuEventsListener menuEventsListener;
    private float initialTouchX;
    private float initialTouchY;
    private float startX, startY;
    private float prevX, prevY;
    private int mTouchSlop;

    public MainActionMenu(@NonNull Context context, ActionMenuEventsListener listener) {
        super(context);
        initUI(context);
        menuEventsListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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

        ViewConfiguration mViewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = mViewConfiguration.getScaledTouchSlop();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.menu_main_fab) {
            expand();
        } else if (id == R.id.menu_dismiss_fab) {
            collapse();
        } else if (id == R.id.menu_settings_fab) {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } else if (id == R.id.menu_features_fab) {
            Intent intent = new Intent(getContext(), RequestActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isScrolling = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                prevX = startX;
                prevY = startY;
                isScrolling = false;
                return false;
            case MotionEvent.ACTION_MOVE:
                if (isScrolling)
                    return true;

                int Xdiff = (int) (event.getRawX() - startX);
                int Ydiff = (int) (event.getRawY() - startY);
                if (Math.abs(Ydiff) > mTouchSlop || Math.abs(Xdiff) > mTouchSlop) {
                    // Start scrolling!
                    isScrolling = true;
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currX = event.getRawX();
        float currY = event.getRawY();
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = currX;
//                startY = currY;
//                prevX  = currX;
//                prevY  = currY;
//                break;
            case MotionEvent.ACTION_MOVE:
                if (menuEventsListener != null) {
                    float dX = (currX - prevX);
                    float dY = (currY - prevY);
                    prevX = currX;
                    prevY = currY;
                    menuEventsListener.OnMenuMoved(dX, dY);
                }
                break;
        }
        return true;
    }


    public interface ActionMenuEventsListener {
        void OnMenuMoved(float dx, float dy);
    }
}
