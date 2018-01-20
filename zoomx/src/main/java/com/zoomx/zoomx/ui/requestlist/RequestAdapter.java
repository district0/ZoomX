package com.zoomx.zoomx.ui.requestlist;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> implements Filterable {

    private List<RequestEntity> requests = new ArrayList<>();
    private OnRequestItemClickListener onRequestItemClickListener;
    private List<RequestEntity> mFilteredList;

    public RequestAdapter() {
    }

    public void setRequestEntityList(List<RequestEntity> requestEntityList, OnRequestItemClickListener
            onRequestItemClickListener) {
        this.requests = requestEntityList;
        mFilteredList = requestEntityList;
        this.onRequestItemClickListener = onRequestItemClickListener;
        notifyDataSetChanged();
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_row, parent, false);

        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        holder.bind(this.mFilteredList.get(position), onRequestItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (mFilteredList == null)
            return 0;
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<RequestEntity> filteredList = new ArrayList<>();

                if (TextUtils.isEmpty(constraint)) {
                    filteredList = requests;
                } else {
                    for (RequestEntity request : requests) {
                        if ((String.valueOf(request.getCode())).equals(constraint)) {

                            filteredList.add(request);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (List<RequestEntity>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnRequestItemClickListener {
        void onItemClick(RequestEntity requestEntity);
    }
}
