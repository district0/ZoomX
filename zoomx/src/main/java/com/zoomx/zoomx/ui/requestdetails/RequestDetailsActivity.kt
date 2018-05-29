package com.zoomx.zoomx.ui.requestdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.zoomx.zoomx.R
import com.zoomx.zoomx.ui.requestlist.RequestListFragment

class RequestDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (intent != null && intent.extras != null) {
            val requestId = intent.extras!!.getInt(RequestListFragment.REQUEST_ID, 1)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, RequestDetailsFragment.newInstance(requestId))
                    .commit()
        }

    }


}
