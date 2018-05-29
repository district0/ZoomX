package com.zoomx.zoomx.ui.requestdetails

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.zoomx.zoomx.R

/**
 * Created by Ibrahim AbdelGawad on 12/14/2017.
 */

class RequestDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val headerKeyTextView: TextView
    private val headerValueTextView: TextView
    private val headerLinearLayout: LinearLayout

    init {
        headerKeyTextView = itemView.findViewById(R.id.key_tx)
        headerValueTextView = itemView.findViewById(R.id.value_tx)
        headerLinearLayout = itemView.findViewById(R.id.header_layout)
    }

    fun bind(key: String, value: String, position: Int) {
        headerKeyTextView.text = key
        headerValueTextView.text = value

        if (key.equals(itemView.context.getString(R.string.response_headers), ignoreCase = true) or key.equals(itemView.context.getString(R.string.request_headers), ignoreCase = true)) {
            headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey_200))
            headerKeyTextView.setAllCaps(true)
            headerKeyTextView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            headerValueTextView.visibility = View.GONE
        } else if (position % 2 == 0) {
            headerLinearLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey_100))
            headerValueTextView.visibility = View.VISIBLE
        } else {
            headerLinearLayout.setBackgroundColor(Color.WHITE)
            headerValueTextView.visibility = View.VISIBLE
        }
    }
}
