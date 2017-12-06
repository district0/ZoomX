package com.zoomx.zoomx.ui.requestlist.request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestActivity extends AppCompatActivity
{
    private List<RequestModel> requestModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity_layout);

        requestModelList = prepareRequestData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        requestAdapter = new RequestAdapter(requestModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);
    }

    public List<RequestModel> prepareRequestData() {
        List <RequestModel> requestModelList = new ArrayList<>();
        RequestModel requestModel = new RequestModel();
        RequestModel requestModel2 = new RequestModel();
        RequestModel requestModel3 = new RequestModel();


        requestModel.setUrl("www.google.com");
        requestModel.setCode(200);

        requestModel2.setUrl("www.google.com");
        requestModel2.setCode(200);

        requestModel3.setUrl("www.google.com");
        requestModel3.setCode(200);

        requestModelList.add(requestModel);
        requestModelList.add(requestModel2);
        requestModelList.add(requestModel3);

        return requestModelList;
    }
}

