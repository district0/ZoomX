package com.zoomx.zoomx.ui.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;

import com.zoomx.zoomx.R;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private SettingsManager mSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUi();
    }

    private void initUi() {
        mSettingsManager = SettingsManager.get(this);
        initToolBar();
        initSwitch(R.id.setting_network_tracker_switch, mSettingsManager.isNetworkTrackingEnabled());
        initSwitch(R.id.setting_initail_show_switch, mSettingsManager.canShowMenuOnAppStart());
    }

    private void initSwitch(int id, boolean isChecked) {
        SwitchCompat switchCompat = findViewById(id);
        switchCompat.setOnCheckedChangeListener(this);
        switchCompat.setChecked(isChecked);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.setting_screen_title);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.setting_network_tracker_switch) {
            mSettingsManager.setNetworkTrackingStatus(isChecked);
        } else if (id == R.id.setting_initail_show_switch) {
            mSettingsManager.setShowMenuOnAppStart(isChecked);
        }
    }
}