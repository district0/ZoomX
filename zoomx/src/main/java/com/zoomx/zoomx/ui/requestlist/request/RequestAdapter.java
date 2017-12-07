package com.zoomx.zoomx.ui.requestlist.request;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    List<RequestEntity> RequestEntityList = new ArrayList<>();

    public RequestAdapter(List<RequestEntity> RequestEntityList) {
        this.RequestEntityList = RequestEntityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RequestEntity RequestEntity = RequestEntityList.get(position);
        holder.url.setText(RequestEntity.getUrl());
        holder.details.setText(RequestEntity.getCode() + " " + RequestEntity.getMethod() + " " + RequestEntity.getStartDate());
    }

    @Override
    public int getItemCount() {
        return RequestEntityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView url, details;

        public MyViewHolder(View view) {
            super(view);
            url = (TextView) view.findViewById(R.id.url);
            details = (TextView) view.findViewById(R.id.details);
        }
    }
}
