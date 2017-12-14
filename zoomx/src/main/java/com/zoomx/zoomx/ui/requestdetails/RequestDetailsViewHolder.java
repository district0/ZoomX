package com.zoomx.zoomx.ui.requestdetails;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zoomx.zoomx.R;

/**
 * Created by Ibrahim AbdelGawad on 12/14/2017.
 */

public class RequestDetailsViewHolder extends RecyclerView.ViewHolder {
    private TextView headerKeyTextView, headerValueTextView;

    public RequestDetailsViewHolder(View itemView) {
        super(itemView);
        headerKeyTextView = itemView.findViewById(R.id.key_tx);
        headerValueTextView = itemView.findViewById(R.id.value_tx);
    }

    public void bind(String key, String value, int position) {
        headerKeyTextView.setText(key);
        headerValueTextView.setText(value);

        if (position % 2 == 0) {
            headerKeyTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray_100));
            headerValueTextView.setBackgroundColor(Color.WHITE);
        } else {
            headerKeyTextView.setBackgroundColor(Color.WHITE);
            headerValueTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray_100));
        }
    }
}
