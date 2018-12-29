
package com.zoomx.zoomx.ui.requestdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoomx.zoomx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */
public class RequestDetailsAdapter extends RecyclerView.Adapter<RequestDetailsViewHolder> {

    private ArrayList<String> requestHeaderList;

    public RequestDetailsAdapter(ArrayList<String> requestHeaderList, ArrayList<String> responseHeadersMap) {
        this.requestHeaderList = new ArrayList<>();
        this.requestHeaderList.add("Request:Request");
        this.requestHeaderList.addAll(requestHeaderList);
        this.requestHeaderList.add("Response:Response");
        this.requestHeaderList.addAll(responseHeadersMap);
    }

    @Override
    public RequestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_details_item_row, parent, false);

        return new RequestDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestDetailsViewHolder holder, int position) {
        try {
            holder.bind(requestHeaderList.get(position), position);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return requestHeaderList.size();
    }
}
