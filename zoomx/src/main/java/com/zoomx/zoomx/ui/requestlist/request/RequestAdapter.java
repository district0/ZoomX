package com.zoomx.zoomx.ui.requestlist.request;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> implements Filterable {

    private List<RequestEntity> requests = new ArrayList<>();
    private List<RequestEntity> mFilteredList;

    public RequestAdapter() {
    }

    public void setRequestEntityList(List<RequestEntity> requestEntityList) {
        this.requests = requestEntityList;
        mFilteredList = requestEntityList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RequestEntity requestEntity = mFilteredList.get(position);
        holder.urlTextView.setText(requestEntity.getUrl());
        holder.methodTypeTextView.setText(requestEntity.getMethod());
        holder.codeTextView.setText(String.valueOf(requestEntity.getCode()));
        holder.codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.getCode()));
        holder.startDateTextView.setText(FormatUtil.formatDate(requestEntity.getStartDate(),FormatUtil.DATE_FORMAT));
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
                        if ((request.getCode() + "").equals(constraint)) {

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView urlTextView, codeTextView, methodTypeTextView, startDateTextView;

        public MyViewHolder(View view) {
            super(view);
            urlTextView = view.findViewById(R.id.url_tx);
            codeTextView = view.findViewById(R.id.code_txt);
            methodTypeTextView = view.findViewById(R.id.method_txt);
            startDateTextView = view.findViewById(R.id.date_txt);

        }
    }
}
