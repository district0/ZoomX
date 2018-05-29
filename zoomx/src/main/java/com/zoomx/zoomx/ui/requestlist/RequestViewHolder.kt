package com.zoomx.zoomx.ui.requestlist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.util.ColorUtils
import com.zoomx.zoomx.util.ErrorConstants
import com.zoomx.zoomx.util.FormatUtil

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */

class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var urlTextView: TextView
    internal var codeTextView: TextView
    internal var methodTypeTextView: TextView
    internal var startDateTextView: TextView

    init {
        urlTextView = view.findViewById(R.id.url_tx)
        codeTextView = view.findViewById(R.id.details_code_txt)
        methodTypeTextView = view.findViewById(R.id.details_method_txt)
        startDateTextView = view.findViewById(R.id.date_txt)
    }

    fun bind(requestEntity: RequestEntity?, onRequestItemClickListener: RequestAdapter.OnRequestItemClickListener?) {
        if (requestEntity != null) {
            urlTextView.text = requestEntity.url
            methodTypeTextView.text = requestEntity.method
            codeTextView.text = if (requestEntity.code == ErrorConstants.getCONNECTION_ERROR())
                itemView.context.getString(R.string.request_error_code_text)
            else
                requestEntity.code.toString()
            codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.code, itemView.context))
            startDateTextView.text = FormatUtil.formatDate(requestEntity.startDate, FormatUtil.getDATE_FORMAT())
            itemView.setOnClickListener {
                onRequestItemClickListener?.onItemClick(requestEntity)
            }
        }

    }
}
