package com.zoomx.zoomx.ui.requestlist;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.ui.requestdetails.RequestDetailsActivity;

import java.util.List;

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

public class RequestListFragment extends Fragment implements SearchView.OnQueryTextListener, RequestAdapter.OnRequestItemClickListener {

    public static final String REQUEST_ID = "requestId";
    private RequestAdapter requestAdapter;
    private RequestListViewModel viewModel;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_list_fragment, container, false);
        setHasOptionsMenu(true);
        ((RequestActivity) getActivity()).getSupportActionBar().setTitle(R.string.request_screen_title);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        requestAdapter = new RequestAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        setRequestData();

        return view;
    }

    public void setRequestData() {
        viewModel = ViewModelProviders.of(this).get(RequestListViewModel.class);
        viewModel.getRequests().observe(this, new Observer<List<RequestEntity>>() {
            @Override
            public void onChanged(@Nullable List<RequestEntity> requestEntities) {
                if (searchView != null && searchView.isIconified())
                    requestAdapter.setRequestEntityList(requestEntities, RequestListFragment.this);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_request_list, menu);
        prepareSearchView(menu);
    }

    private void prepareSearchView(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search_requests).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setBackgroundColor(Color.WHITE);
        /* Code for changing the textcolor and hint color for the search view */
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
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
        Intent intent = new Intent(getActivity(), RequestDetailsActivity.class);
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
