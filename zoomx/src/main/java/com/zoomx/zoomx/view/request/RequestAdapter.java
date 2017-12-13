package com.zoomx.zoomx.view.request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.util.ColorUtils;
import com.zoomx.zoomx.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {

    private List<RequestEntity> requests = new ArrayList<>();
    private OnRequestItemClickListener onRequestItemClickListener;
    private Context context;

    public RequestAdapter() {
    }

    public void setRequestEntityList(List<RequestEntity> requestEntityList , OnRequestItemClickListener
            onRequestItemClickListener , Context context) {
        this.requests = requestEntityList;
        this.onRequestItemClickListener = onRequestItemClickListener;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_row, parent, false);

        return new RequestViewHolder(context ,view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        holder.bind(this.requests.get(position) , onRequestItemClickListener );
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

}
