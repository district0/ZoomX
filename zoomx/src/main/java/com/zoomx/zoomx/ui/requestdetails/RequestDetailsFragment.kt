package com.zoomx.zoomx.ui.requestdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.zoomx.zoomx.BuildConfig
import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.ui.requestlist.RequestListFragment
import com.zoomx.zoomx.util.ColorUtils
import com.zoomx.zoomx.util.FormatUtil
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

class RequestDetailsFragment : Fragment() {
    private var viewModel: RequestDetailsViewModel? = null
    private var mRequestEntity: RequestEntity? = null
    private var view: View? = null
    private var requestLayout: RelativeLayout? = null
    private var responseLayout: RelativeLayout? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.request_details_fragment, container, false)
        (activity as RequestDetailsActivity).supportActionBar!!.setTitle(R.string.request_details_screen_title)
        setHasOptionsMenu(true)
        if (arguments != null && arguments.containsKey(RequestListFragment.REQUEST_ID)) {
            val requestId = arguments.get(RequestListFragment.REQUEST_ID) as Int
            viewModel = ViewModelProviders.of(this).get(RequestDetailsViewModel::class.java)
            viewModel!!.getRequestById(requestId).observe(this, Observer { requestEntity ->
                val item = HashMap<String, String>()
                if (requestEntity != null) {
                    item[RequestListFragment.REQUEST_ID] = requestEntity.method
                    initUi(requestEntity)
                }
            })
        }
        return view
    }

    private fun initUi(requestEntity: RequestEntity?) {
        mRequestEntity = requestEntity
        val methodTextView = view!!.findViewById<TextView>(R.id.request_details_method_txt)
        val codeTextView = view!!.findViewById<TextView>(R.id.request_details_code_txt)
        val urlTextView = view!!.findViewById<TextView>(R.id.request_details_url_txt)
        val dateTextView = view!!.findViewById<TextView>(R.id.request_details_startDate_txt)
        responseLayout = view!!.findViewById(R.id.request_details_response_layout)
        requestLayout = view!!.findViewById(R.id.request_details_request_body_layout)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.request_details_recycler_view)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.itemAnimator = DefaultItemAnimator()
        val requestDetailsAdapter = RequestDetailsAdapter(getHeadersKeyList(getHeaderMap(requestEntity)), getHeadersValueList(getHeaderMap(requestEntity)))

        recyclerView.adapter = requestDetailsAdapter
        methodTextView.text = requestEntity!!.method
        codeTextView.text = requestEntity.code.toString()
        codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.code, context))
        urlTextView.text = requestEntity.url
        dateTextView.text = FormatUtil.formatDate(requestEntity.startDate, FormatUtil.getDATE_FORMAT())

        responseLayout!!.setOnClickListener { showBody(requestEntity.url, requestEntity.responseBody) }

        requestLayout!!.setOnClickListener { showBody(requestEntity.url, requestEntity.requestBody) }
    }

    fun showBody(url: String?, body: String?) {
        val intent = Intent(activity, JsonViewActivity::class.java)
        intent.putExtra(BODY_URL_KEY, url)
        intent.putExtra(BODY_JSON_KEY, body)
        startActivity(intent)
    }

    fun getHeaderMap(requestEntity: RequestEntity?): Map<String, String> {
        val headersMap = HashMap<String, String>()
        if (requestEntity!!.responseHeaders != null && requestEntity.responseHeaders!!.getHeadersMap() != null) {
            headersMap[getString(R.string.response_headers)] = ""
            for ((key, value) in requestEntity.responseHeaders!!.getHeadersMap()!!) {
                headersMap[key] = value
            }
        }

        if (requestEntity.requestHeaders != null && requestEntity.requestHeaders!!.getHeadersMap() != null) {
            headersMap[getString(R.string.request_headers)] = ""
            for ((key, value) in requestEntity.requestHeaders!!.getHeadersMap()!!) {
                headersMap[key] = value
            }
        }

        return headersMap
    }

    fun getHeadersKeyList(map: Map<String, String>): List<*> {
        return ArrayList(map.keys)
    }

    fun getHeadersValueList(map: Map<String, String>): List<*> {
        return ArrayList(map.values)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_requests_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_share_request) {
            shareRequest()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareRequest() {
        RequestToFileTask().execute(mRequestEntity)
    }


    inner class RequestToFileTask : AsyncTask<RequestEntity, Void, File>() {

        private val fileName: String
            get() = Uri.parse(mRequestEntity!!.url).lastPathSegment

        override fun doInBackground(vararg requestEntities: RequestEntity): File? {
            try {
                val requestEntity = requestEntities[0]
                val tmpFile = File.createTempFile(fileName, ".txt", activity.cacheDir)
                val writer = FileWriter(tmpFile)
                writer.write(requestEntity.url!!)
                writer.append("\n")
                writer.append(requestEntity.requestBody)
                writer.append("\n")
                writer.append(requestEntity.responseBody)
                writer.close()
                return tmpFile
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }


        override fun onPostExecute(file: File?) {
            if (file != null) {
                val sharedFileUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID, file)
                val share = ShareCompat.IntentBuilder.from(activity)
                        .setType(TEXT_DATA_TYPE)
                        .setStream(sharedFileUri)
                        .setChooserTitle(R.string.share_file_chooser_title)
                        .createChooserIntent()
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(share)

            }
        }

        private fun grandReadPermissions(intent: Intent, sharedFileUri: Uri) {
            val packageManager = activity.packageManager
            val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolvedIntentInfo in activities) {
                val packageName = resolvedIntentInfo.activityInfo.packageName
                activity.grantUriPermission(packageName, sharedFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }
    }

    companion object {

        val BODY_URL_KEY = "url"
        val BODY_JSON_KEY = "body"
        private val TEXT_DATA_TYPE = "text/plain"


        fun newInstance(requestId: Int): RequestDetailsFragment {
            val args = Bundle()
            args.putInt(RequestListFragment.REQUEST_ID, requestId)
            val fragment = RequestDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
