package com.zoomx.zoomx.ui.info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.zoomx.zoomx.R

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        initUI()
    }

    private fun initUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val infoFragment = InfoFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, infoFragment)
                .commit()
    }

}
