package com.zoomx.zoomx.ui.requestlist.request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestActivity extends AppCompatActivity
{
    private List<RequestEntity> RequestEntityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity_layout);

        RequestEntityList = prepareRequestData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        requestAdapter = new RequestAdapter(RequestEntityList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);
    }

    public List<RequestEntity> prepareRequestData() {
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

        return RequestEntityList;
    }
}

