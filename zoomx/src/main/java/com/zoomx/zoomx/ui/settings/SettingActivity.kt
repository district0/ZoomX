package com.zoomx.zoomx.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.zoomx.zoomx.R
import com.zoomx.zoomx.ui.ZoomxUIOption
import kotlinx.android.synthetic.main.activity_settings.*

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var toolbar: Toolbar
    private lateinit var mSettingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initUi()
    }

    private fun initUi() {
        mSettingsManager = SettingsManager[this]
        initToolBar()
        initSwitch(R.id.setting_network_tracker_switch, mSettingsManager.isNetworkTrackingEnabled!!)
        initZoomxUiOptions()
    }

    private fun initSwitch(id: Int, isChecked: Boolean) {
        val switchCompat = findViewById<SwitchCompat>(id)
        switchCompat.setOnCheckedChangeListener(this)
        switchCompat.isChecked = isChecked
    }

    private fun initToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.setting_screen_title)
    }

    private fun initZoomxUiOptions() {

        zoomx_ui_options.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.zoomx_ui_options_checkboxes, null, false)

            val defaultChecked = if (mSettingsManager.zoomxUIOption == ZoomxUIOption.NOTIFICATION)
                R.id.radio_notification_option else R.id.radio_draw_over_apps_option

            with(view.findViewById<RadioGroup>(R.id.zoomx_ui_options_radios)) {
                check(defaultChecked)
                setOnCheckedChangeListener { _, checkedId ->
                    mSettingsManager.zoomxUIOption = if (checkedId == R.id.radio_notification_option)
                        ZoomxUIOption.NOTIFICATION else ZoomxUIOption.DRAW_OVER_APPS
                }
            }
            val dialog: AlertDialog = AlertDialog
                    .Builder(this)
                    .setTitle(getString(R.string.zoomx_ui_chooser))
                    .setView(view)
                    .create()

            dialog.show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val id = buttonView.id
        if (id == R.id.setting_network_tracker_switch) {
            mSettingsManager.setNetworkTrackingStatus(isChecked)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SettingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }
}