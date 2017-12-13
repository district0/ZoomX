package com.zoomx.zoomx.view.requestdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.view.request.RequestActivity;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestDetailsAdapter requestDetailsAdapter;
    private RequestDetailsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(RequestActivity.REQUEST_ID)) {
            int requestId = (int) getIntent().getExtras().get(RequestActivity.REQUEST_ID);
            viewModel = ViewModelProviders.of(this).get(RequestDetailsViewModel.class);
            viewModel.getRequestById(requestId).observe(this, new Observer<RequestEntity>() {
                @Override
                public void onChanged(@Nullable RequestEntity requestEntity) {
                    Map<String,String> item = new HashMap<>();
                    item.put(RequestActivity.REQUEST_ID,requestEntity.getMethod());
                    requestDetailsAdapter = new RequestDetailsAdapter(item);
                    recyclerView.setAdapter(requestDetailsAdapter);
                }
            });

        }
    }
}
