package com.zoomx.zoomx.ui.response;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.ui.requestdetails.RequestDetailsActivity;

public class BodyActivity extends AppCompatActivity {

    private TextView bodyTextView;
    private TextView urlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().
                containsKey(RequestDetailsActivity.BODY_URL_KEY)) {
            String url = (String) getIntent().getExtras().get(RequestDetailsActivity.BODY_URL_KEY);
            String body = (String) getIntent().getExtras().get(RequestDetailsActivity.BODY_JSON_KEY);
            initUi(url, body);
        }
    }

    public void initUi(String url, String body) {
        urlTextView = findViewById(R.id.body_url_txt);
        bodyTextView = findViewById(R.id.body_json_txt);
        urlTextView.setText(url);
        bodyTextView.setText(body);
    }
}
