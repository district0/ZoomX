
package com.zoomx.zoomx.view.requestdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomx.zoomx.R;

import java.util.Map;

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */
public class RequestDetailsAdapter extends RecyclerView.Adapter<RequestDetailsAdapter.MyRequestDetailsViewHolder> {

    private Map<String, String> itemsMap;

    public RequestDetailsAdapter(Map<String, String> itemsMap) {
        this.itemsMap = itemsMap;
    }

    @Override
    public MyRequestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_details_item_row, parent, false);

        return new MyRequestDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRequestDetailsViewHolder holder, int position) {
        holder.keyTextView.setText(this.itemsMap.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsMap.size();
    }

    public class MyRequestDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView keyTextView, valueTextView;

        public MyRequestDetailsViewHolder(View itemView) {
            super(itemView);

            keyTextView = itemView.findViewById(R.id.key_tx);
            valueTextView = itemView.findViewById(R.id.value_tx);
        }
    }
}
