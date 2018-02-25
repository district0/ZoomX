package com.zoomx.zoomx.ui.requestdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.ui.requestlist.RequestListFragment;

public class RequestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null && getIntent().getExtras() != null) {
            int requestId = getIntent().getExtras().getInt(RequestListFragment.REQUEST_ID, 1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, RequestDetailsFragment.newInstance(requestId))
                    .commit();
        }

    }


}
