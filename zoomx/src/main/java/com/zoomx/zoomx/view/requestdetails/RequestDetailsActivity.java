package com.zoomx.zoomx.view.requestdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.util.ColorUtils;
import com.zoomx.zoomx.view.request.RequestActivity;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestDetailsAdapter requestDetailsAdapter;
    private RequestDetailsViewModel viewModel;
    private TextView methodTextView, codeTextView, urlTextView, dateTextView;
    private ImageView responseArrowImageView;

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

    private void initUi(RequestEntity requestEntity) {
        methodTextView = findViewById(R.id.request_details_method_txt);
        codeTextView = findViewById(R.id.request_details_code_txt);
        urlTextView = findViewById(R.id.request_details_url_txt);
        dateTextView = findViewById(R.id.request_details_startDate_txt);
        responseArrowImageView = findViewById(R.id.request_details_arrow_img);

/*        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/

        methodTextView.setText(requestEntity.getMethod());
        codeTextView.setText(requestEntity.getCode() + "");
        codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.getCode()));
        urlTextView.setText(requestEntity.getUrl());
        dateTextView.setText(requestEntity.getStartDate().toString());
    }
}
