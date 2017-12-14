package com.zoomx.zoomx.ui.request;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.util.ColorUtils;
import com.zoomx.zoomx.util.FormatUtil;

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */

public class RequestViewHolder extends RecyclerView.ViewHolder {
    TextView urlTextView, codeTextView, methodTypeTextView, startDateTextView;

    public RequestViewHolder(View view) {
        super(view);
        urlTextView = view.findViewById(R.id.url_tx);
        codeTextView = view.findViewById(R.id.details_code_txt);
        methodTypeTextView = view.findViewById(R.id.details_method_txt);
        startDateTextView = view.findViewById(R.id.date_txt);
    }

    public void bind(final RequestEntity requestEntity, final RequestAdapter.OnRequestItemClickListener onRequestItemClickListener) {
        if (requestEntity != null) {
            urlTextView.setText(requestEntity.getUrl());
            methodTypeTextView.setText(requestEntity.getMethod());
            codeTextView.setText(String.valueOf(requestEntity.getCode()));
            codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.getCode()));
            startDateTextView.setText(FormatUtil.formatDate(requestEntity.getStartDate(), FormatUtil.DATE_FORMAT));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRequestItemClickListener != null) {
                        onRequestItemClickListener.onItemClick(requestEntity);
                    }
                }
            });
        }

    }
}
