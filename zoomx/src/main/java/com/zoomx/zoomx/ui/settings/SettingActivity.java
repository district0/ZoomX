package com.zoomx.zoomx.ui.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.zoomx.zoomx.R;

public class SettingActivity extends AppCompatActivity {

    private SwitchCompat networkTrackerSwitch;
    public static final String NETWORK_TRACKER_KEY = "networkTrackerKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUi();
    }

    private void initUi() {
        networkTrackerSwitch = findViewById(R.id.setting_network_tracker_switch);
        networkTrackerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsManager.get(getApplicationContext()).setNetworkTrackingStatus(isChecked);
            }
        });
        networkTrackerSwitch.setChecked(SettingsManager.get(getApplicationContext()).isNetworkTrackingEnabled());
    }
}
