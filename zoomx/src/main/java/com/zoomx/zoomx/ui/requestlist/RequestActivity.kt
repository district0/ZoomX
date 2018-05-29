package com.zoomx.zoomx.ui.requestlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.zoomx.zoomx.R

/**
 * Created by Ibrahim AbdelGawad on 12/3/2017.
 */

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_activity_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, RequestListFragment())
                .commit()
    }
}

