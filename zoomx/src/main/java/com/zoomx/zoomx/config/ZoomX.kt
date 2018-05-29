package com.zoomx.zoomx.config

import android.annotation.SuppressLint
import android.util.Log

import com.zoomx.zoomx.db.AppDatabase
import com.zoomx.zoomx.db.RequestDao
import com.zoomx.zoomx.ui.menu.ZoomxMenuService
import com.zoomx.zoomx.util.ShakeEventManager

/**
 * Created by Ahmed Fathallah on 12/10/2017.
 */

object ZoomX {

    private val TAG = "ZoomX:Manager"

    @SuppressLint("StaticFieldLeak")
    private var config: Config? = null
    var requestDao: RequestDao? = null
        private set
    private var mShakeEventManager: ShakeEventManager? = null

    fun init(config: Config) {
        ZoomX.config = config
        checkPreConditions()
        setupDataBase()
        handleShowMenuOnStart()
        handleShowMenuOnShakeEvent()
    }

    private fun checkPreConditions() {
        if (config!!.canShowOnShakeEvent() && config!!.canShowMenuOnAppStart()) {
            Log.d(TAG, "You should only enable show On shake or on app start")
        }
    }

    private fun handleShowMenuOnStart() {
        if (config!!.canShowMenuOnAppStart() && !config!!.canShowOnShakeEvent()) {
            showMenuHead()
        }
    }

    private fun setupDataBase() {
        val database = AppDatabase.get(config!!.context)
        requestDao = database.requestDao()
    }

    fun showMenuHead() {
        ZoomxMenuService.showMenuHead(config!!.context)
    }

    fun hideMenuHead() {
        ZoomxMenuService.hideMenuHead(config!!.context)
    }

    fun handleShowMenuOnShakeEvent() {
        Log.d(TAG, "Show Menu on shake called")
        if (config!!.canShowOnShakeEvent() && !config!!.canShowMenuOnAppStart()) {
            Log.d(TAG, "Show Menu on shake executed")
            mShakeEventManager = ShakeEventManager(config!!.context)
            mShakeEventManager!!.listen {
                Log.d(TAG, "shake detected")
                showMenuHead()
            }
        }
    }
}
