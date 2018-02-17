package com.zoomx.zoomx.ui.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zoomx.zoomx.R;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InfoFragment infoFragment = new InfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, infoFragment)
                .commit();
    }

}
