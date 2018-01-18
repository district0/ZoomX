package com.zoomx.zoomx.ui.response;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.ui.requestdetails.RequestDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BodyActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.json_viewer_screen_title);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().
                containsKey(RequestDetailsActivity.BODY_URL_KEY)) {
            String url = (String) getIntent().getExtras().get(RequestDetailsActivity.BODY_URL_KEY);
            String body = (String) getIntent().getExtras().get(RequestDetailsActivity.BODY_JSON_KEY);
            initUi(url, body);
        }
    }

    public void initUi(String url, String body) {
        TextView urlTextView = findViewById(R.id.body_url_txt);
        TextView bodyTextView = findViewById(R.id.body_json_txt);
        urlTextView.setText(url);
        bodyTextView.setText(formatJsonPretty(body));
    }

    /**
     * Convert a JSON string to pretty  version
     *
     * @param jsonString
     * @return
     */
    public String formatJsonPretty(String jsonString) {
        Object json = null;
        try {
            json = new JSONTokener(jsonString).nextValue();
            if (json instanceof JSONObject) {
                return ((JSONObject) json).toString(8);
            } else if (json instanceof JSONArray) {
                return ((JSONArray) json).toString(8);
            } else {
                return jsonString;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
