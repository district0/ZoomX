package com.zoomx.zoomx.ui.requestdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.ui.request.RequestActivity;
import com.zoomx.zoomx.ui.response.BodyActivity;
import com.zoomx.zoomx.util.ColorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestDetailsAdapter requestDetailsAdapter;
    private RequestDetailsViewModel viewModel;
    private TextView methodTextView, codeTextView, urlTextView, dateTextView;
    private ImageView responseArrowImageView, requestArrowImageView;
    public static final String BODY_URL_KEY = "url";
    public static final String BODY_JSON_KEY = "body";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(RequestActivity.REQUEST_ID)) {
            int requestId = (int) getIntent().getExtras().get(RequestActivity.REQUEST_ID);
            viewModel = ViewModelProviders.of(this).get(RequestDetailsViewModel.class);
            viewModel.getRequestById(requestId).observe(this, new Observer<RequestEntity>() {
                @Override
                public void onChanged(@Nullable RequestEntity requestEntity) {
                    Map<String, String> item = new HashMap<>();
                    if (requestEntity != null) {
                        item.put(RequestActivity.REQUEST_ID, requestEntity.getMethod());
                        initUi(requestEntity);
                    }
                }
            });
        }
    }

    private void initUi(final RequestEntity requestEntity) {
        methodTextView = findViewById(R.id.request_details_method_txt);
        codeTextView = findViewById(R.id.request_details_code_txt);
        urlTextView = findViewById(R.id.request_details_url_txt);
        dateTextView = findViewById(R.id.request_details_startDate_txt);
        responseArrowImageView = findViewById(R.id.request_details_response_body_arrow_img);
        requestArrowImageView = findViewById(R.id.request_details_request_body_arrow_img);
        recyclerView = findViewById(R.id.request_details_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        requestDetailsAdapter = new RequestDetailsAdapter(getHeadersKeyList(getHeaderMap(requestEntity))
                , getHeadersValueList(getHeaderMap(requestEntity)));

        recyclerView.setAdapter(requestDetailsAdapter);
        methodTextView.setText(requestEntity.getMethod());
        codeTextView.setText(requestEntity.getCode() + "");
        codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.getCode(), this));
        urlTextView.setText(requestEntity.getUrl());
        dateTextView.setText(requestEntity.getStartDate().toString());

        responseArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBody(requestEntity.getUrl(), requestEntity.getResponseBody());
            }
        });

        requestArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBody(requestEntity.getUrl(), requestEntity.getRequestBody());
            }
        });
    }

    public void showBody(String url, String body) {
        Intent intent = new Intent(RequestDetailsActivity.this, BodyActivity.class);
        intent.putExtra(BODY_URL_KEY, url);
        intent.putExtra(BODY_JSON_KEY, body);
        startActivity(intent);
    }

    public Map<String, String> getHeaderMap(RequestEntity requestEntity) {
        Map<String, String> headersMap = new HashMap<>();
        if (requestEntity.getResponseHeaders() != null && requestEntity.getResponseHeaders().getHeadersMap() != null) {
            headersMap.put(getString(R.string.response_headers), "");
            for (Map.Entry<String, String> entry : requestEntity.getResponseHeaders().getHeadersMap().entrySet()) {
                headersMap.put(entry.getKey(), entry.getValue());
            }
        }

        if (requestEntity.getRquestHeaders() != null && requestEntity.getRquestHeaders().getHeadersMap() != null) {
            headersMap.put(getString(R.string.request_headers), "");
            for (Map.Entry<String, String> entry : requestEntity.getRquestHeaders().getHeadersMap().entrySet()) {
                headersMap.put(entry.getKey(), entry.getValue());
            }
        }

        return headersMap;
    }

    public List getHeadersKeyList(Map<String, String> map) {
        List<String> list = new ArrayList<String>(map.keySet());
        return list;
    }

    public List getHeadersValueList(Map<String, String> map) {
        List<String> list = new ArrayList<String>(map.values());
        return list;
    }
}
