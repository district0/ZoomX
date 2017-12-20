
package com.zoomx.zoomx.ui.requestdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoomx.zoomx.R;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */
public class RequestDetailsAdapter extends RecyclerView.Adapter<RequestDetailsViewHolder> {

    private List keysList, valuesList;

    public RequestDetailsAdapter(List keysList, List valuesList) {
        this.keysList = keysList;
        this.valuesList = valuesList;
    }

    @Override
    public RequestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_details_item_row, parent, false);

        return new RequestDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestDetailsViewHolder holder, int position) {
        holder.bind(keysList.get(position).toString(), valuesList.get(position).toString(), position);
    }

    @Override
    public int getItemCount() {
        return keysList.size();
    }
}
