package com.zoomx.zoomx.ui.requestlist.request;

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
import com.zoomx.zoomx.viewmodel.RequestListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestActivity extends AppCompatActivity
{
    private List<RequestEntity> requestEntityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RequestListViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity_layout);

        recyclerView = findViewById(R.id.recycler_view);
        requestAdapter = new RequestAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        setRequestData();
    }

    public void setRequestData() {
        viewModel = ViewModelProviders.of(this).get(RequestListViewModel.class);
        viewModel.getRequests().observe(this, new Observer<List<RequestEntity>>() {
            @Override
            public void onChanged(@Nullable List<RequestEntity> requestEntities) {
                requestAdapter.setRequestEntityList(requestEntityList);
            }
        });
    }

    public void setDummyRequestData() {
        List<RequestEntity> RequestEntityList = new ArrayList<>();
        RequestEntity RequestEntity = new RequestEntity();
        RequestEntity RequestEntity2 = new RequestEntity();
        RequestEntity RequestEntity3 = new RequestEntity();


        RequestEntity.setUrl("www.google.com");
        RequestEntity.setCode(200);

        RequestEntity2.setUrl("www.google.com");
        RequestEntity2.setCode(200);

        RequestEntity3.setUrl("www.google.com");
        RequestEntity3.setCode(200);

        RequestEntityList.add(RequestEntity);
        RequestEntityList.add(RequestEntity2);
        RequestEntityList.add(RequestEntity3);

        requestAdapter.setRequestEntityList(RequestEntityList);
    }
}

