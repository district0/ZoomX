package com.zoomx.zoomx.ui.requestlist

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.*
import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.ui.requestdetails.RequestDetailsActivity

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

class RequestListFragment : Fragment(), SearchView.OnQueryTextListener, RequestAdapter.OnRequestItemClickListener {
    private var requestAdapter: RequestAdapter? = null
    private var viewModel: RequestListViewModel? = null
    private var searchView: SearchView? = null
    private val toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.request_list_fragment, container, false)
        setHasOptionsMenu(true)
        (activity as RequestActivity).supportActionBar!!.setTitle(R.string.request_screen_title)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        requestAdapter = RequestAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = requestAdapter

        setRequestData()

        return view
    }

    fun setRequestData() {
        viewModel = ViewModelProviders.of(this).get(RequestListViewModel::class.java)
        viewModel!!.requests.observe(this, Observer { requestEntities ->
            if (searchView != null && searchView!!.isIconified)
                requestAdapter!!.setRequestEntityList(requestEntities as MutableList<RequestEntity>, this@RequestListFragment)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_request_list, menu)
        prepareSearchView(menu!!)
    }

    private fun prepareSearchView(menu: Menu) {
        // Associate searchable configuration with the SearchView
        val searchManager = context.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search_requests).actionView as SearchView
        searchView!!.setSearchableInfo(
                searchManager.getSearchableInfo(activity.componentName))
        searchView!!.isSubmitButtonEnabled = true
        searchView!!.setOnQueryTextListener(this)
        searchView!!.setBackgroundColor(Color.WHITE)
        /* Code for changing the textcolor and hint color for the search view */
        val searchAutoComplete = searchView!!.findViewById<View>(android.support.v7.appcompat.R.id.search_src_text) as SearchView.SearchAutoComplete
        searchAutoComplete.setTextColor(Color.GRAY)
        searchAutoComplete.setHintTextColor(Color.GRAY)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_delete_requests) {
            viewModel!!.clearRequestFromDB().execute()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(requestEntity: RequestEntity) {
        val intent = Intent(activity, RequestDetailsActivity::class.java)
        intent.putExtra(REQUEST_ID, requestEntity.id)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        requestAdapter!!.filter.filter(newText)
        return true
    }

    companion object {

        val REQUEST_ID = "requestId"
    }
}
