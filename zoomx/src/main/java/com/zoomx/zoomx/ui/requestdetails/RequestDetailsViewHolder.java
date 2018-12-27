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

    public void bind(String header, int position) {
        String headerKeyAndValue[] = header.split(":");
        if (headerKeyAndValue.length > 1) {
            String key = headerKeyAndValue[0];
            String value = headerKeyAndValue[1];

            headerKeyTextView.setText(key);
            headerValueTextView.setText(value);

            if (key.equals("Response") || key.equals("Request")) {
                headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
//                headerKeyTextView.setAllCaps(true);
//                headerKeyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                headerValueTextView.setVisibility(View.GONE);
                headerKeyTextView.append(" Headers");
                headerKeyTextView.setAllCaps(true);
                headerKeyTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            } else if (position % 2 == 0) {
                headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey_100));
                headerValueTextView.setVisibility(View.VISIBLE);
            } else {
                headerLinearLayout.setBackgroundColor(Color.WHITE);
                headerValueTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
