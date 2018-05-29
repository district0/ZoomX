package com.zoomx.zoomx.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.widget.CompoundButton

import com.zoomx.zoomx.R

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private var toolbar: Toolbar? = null
    private var mSettingsManager: SettingsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initUi()
    }

    private fun initUi() {
        mSettingsManager = SettingsManager.get(this)
        initToolBar()
        initSwitch(R.id.setting_network_tracker_switch, mSettingsManager!!.isNetworkTrackingEnabled!!)
    }

    private fun initSwitch(id: Int, isChecked: Boolean) {
        val switchCompat = findViewById<SwitchCompat>(id)
        switchCompat.setOnCheckedChangeListener(this)
        switchCompat.isChecked = isChecked
    }

    private fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.setting_screen_title)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val id = buttonView.id
        if (id == R.id.setting_network_tracker_switch) {
            mSettingsManager!!.setNetworkTrackingStatus(isChecked)
        }
    }
}