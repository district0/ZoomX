package com.zoomx.zoomx.ui.requestlist

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.RequestEntity
import java.util.*

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

class RequestAdapter : RecyclerView.Adapter<RequestViewHolder>(), Filterable {

    private var requests: MutableList<RequestEntity> = ArrayList()
    private var onRequestItemClickListener: OnRequestItemClickListener? = null
    private var mFilteredList: List<RequestEntity>? = null

    fun setRequestEntityList(requestEntityList: MutableList<RequestEntity>, onRequestItemClickListener: OnRequestItemClickListener) {
        this.requests = requestEntityList
        mFilteredList = requestEntityList
        this.onRequestItemClickListener = onRequestItemClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.request_item_row, parent, false)

        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(this.mFilteredList!![position], onRequestItemClickListener)
    }

    override fun getItemCount(): Int {
        return if (mFilteredList == null) 0 else mFilteredList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                var filteredList: MutableList<RequestEntity> = ArrayList()

                if (TextUtils.isEmpty(constraint)) {
                    filteredList = requests
                } else {
                    for (request in requests) {
                        if (request.code.toString() == constraint) {

                            filteredList.add(request)
                        }
                    }
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                mFilteredList = results.values as List<RequestEntity>
                notifyDataSetChanged()
            }
        }
    }

    interface OnRequestItemClickListener {
        fun onItemClick(requestEntity: RequestEntity)
    }
}
