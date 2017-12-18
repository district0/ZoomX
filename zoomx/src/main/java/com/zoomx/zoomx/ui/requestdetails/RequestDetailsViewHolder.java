package com.zoomx.zoomx.ui.requestdetails;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoomx.zoomx.R;

/**
 * Created by Ibrahim AbdelGawad on 12/14/2017.
 */

public class RequestDetailsViewHolder extends RecyclerView.ViewHolder {
    private TextView headerKeyTextView, headerValueTextView;
    private LinearLayout headerLinearLayout;

    public RequestDetailsViewHolder(View itemView) {
        super(itemView);
        headerKeyTextView = itemView.findViewById(R.id.key_tx);
        headerValueTextView = itemView.findViewById(R.id.value_tx);
        headerLinearLayout = itemView.findViewById(R.id.header_layout);
    }

    public void bind(String key, String value, int position) {
        headerKeyTextView.setText(key);
        headerValueTextView.setText(value);

        if (key.equalsIgnoreCase(itemView.getContext().getString(R.string.response_headers)) |
                key.equalsIgnoreCase(itemView.getContext().getString(R.string.request_headers))) {
            headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey_200));
            headerKeyTextView.setAllCaps(true);
            headerKeyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            headerValueTextView.setVisibility(View.GONE);
        } else if (position % 2 == 0) {
            headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey_100));
            headerValueTextView.setVisibility(View.VISIBLE);
        } else {
            headerLinearLayout.setBackgroundColor(Color.WHITE);
            headerValueTextView.setVisibility(View.VISIBLE);
        }
    }
}
