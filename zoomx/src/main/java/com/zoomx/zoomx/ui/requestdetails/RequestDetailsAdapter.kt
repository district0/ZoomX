package com.zoomx.zoomx.ui.requestdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zoomx.zoomx.R

/**
 * Created by Ibrahim AbdelGawad on 12/13/2017.
 */
class RequestDetailsAdapter(private val keysList: List<*>, private val valuesList: List<*>) : RecyclerView.Adapter<RequestDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.request_details_item_row, parent, false)

        return RequestDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestDetailsViewHolder, position: Int) {
        holder.bind(keysList[position].toString(), valuesList[position].toString(), position)
    }

    override fun getItemCount(): Int {
        return keysList.size
    }
}
