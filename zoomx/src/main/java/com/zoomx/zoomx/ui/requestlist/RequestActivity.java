package com.zoomx.zoomx.ui.requestlist;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.ui.requestdetails.RequestDetailsActivity;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RequestAdapter.OnRequestItemClickListener {
    public static final String REQUEST_ID = "requestId";
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RequestListViewModel viewModel;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                if (searchView != null && searchView.isIconified())
                    requestAdapter.setRequestEntityList(requestEntities,
                            RequestActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request_list, menu);
        prepareSearchView(menu);
        return true;
    }

    private void prepareSearchView(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search_requests).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setBackgroundColor(Color.WHITE);
        /* Code for changing the textcolor and hint color for the search view */
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.GRAY);
        searchAutoComplete.setHintTextColor(Color.GRAY);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        requestAdapter.getFilter().filter(newText);
        return true;
    }
}

