package com.zoomx.zoomx.view.request;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.view.requestdetails.RequestDetailsActivity;
import com.zoomx.zoomx.viewmodel.RequestListViewModel;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestActivity extends AppCompatActivity implements OnRequestItemClickListener {
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RequestListViewModel viewModel;
    public static final String REQUEST_ID = "requestId";

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
                requestAdapter.setRequestEntityList(requestEntities,
                        RequestActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_requests) {
            viewModel.clearRequestFromDB().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(RequestEntity requestEntity) {
        Intent intent = new Intent(RequestActivity.this, RequestDetailsActivity.class);
        intent.putExtra(REQUEST_ID, requestEntity.getId());
        startActivity(intent);
    }
}

