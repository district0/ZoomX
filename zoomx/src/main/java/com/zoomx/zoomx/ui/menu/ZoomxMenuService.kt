package com.zoomx.zoomx.ui.menu

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager

/**
 * Created by Ahmed Fathallah on 12/14/2017.
 */

class ZoomxMenuService : Service(), MainActionMenu.ActionMenuEventsListener {

    private var menuHeadLayout: MainActionMenu? = null
    private var menuCloseView: MenuCloseView? = null
    private var mWindowManager: WindowManager? = null
    private var menuParams: WindowManager.LayoutParams? = null

    private val windowOverlayFlag: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE

    override fun onCreate() {
        super.onCreate()
        menuHeadLayout = MainActionMenu(this, this)
        menuCloseView = MenuCloseView(this)
        menuParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                windowOverlayFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        menuParams!!.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        menuParams!!.x = 0
        menuParams!!.y = 100

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null && intent.extras != null) {
            if (SHOW_MENU_KEY == intent.extras!!.getString(MENU_STATE_KEY)) {

                if (menuHeadLayout!!.windowToken == null) {
                    mWindowManager!!.addView(menuHeadLayout, menuParams)
                    // addCloseView();
                }
            } else if (HIDE_MENU_KEY == intent.extras!!.getString(MENU_STATE_KEY)) {
                hideMenu()
            }
        }

        return Service.START_NOT_STICKY
    }

    private fun hideMenu() {
        try {
            if (this.menuHeadLayout != null && menuHeadLayout!!.windowToken != null) {
                this.mWindowManager!!.removeView(this.menuHeadLayout)
                this.menuHeadLayout = null
                this.stopSelf()
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (menuHeadLayout != null) mWindowManager!!.removeView(menuHeadLayout)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun OnMenuMoved(dx: Float, dy: Float) {
        menuCloseView!!.visibility = View.VISIBLE
        menuParams!!.x = (menuParams!!.x - dx).toInt()
        menuParams!!.y = (menuParams!!.y + dy).toInt()
        mWindowManager!!.updateViewLayout(menuHeadLayout, menuParams)
    }

    private fun addCloseView() {
        mWindowManager!!.addView(menuCloseView, buildLayoutParamsForClose())
    }

    private fun buildLayoutParamsForClose(): WindowManager.LayoutParams {
        val x = 0
        val y = 0
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                windowOverlayFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT)
        params.x = x
        params.y = y
        return params
    }

    companion object {

        private val MENU_STATE_KEY = "menuActionState"
        private val SHOW_MENU_KEY = "showMenu"
        private val HIDE_MENU_KEY = "hideMenu"

        fun showMenuHead(context: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(myIntent)
            } else {
                val intent = Intent(context, ZoomxMenuService::class.java)
                val bundle = Bundle()
                bundle.putString(MENU_STATE_KEY, SHOW_MENU_KEY)
                intent.putExtras(bundle)
                context.startService(intent)
            }
        }

        fun hideMenuHead(context: Context) {
            val intent = Intent(context, ZoomxMenuService::class.java)
            val bundle = Bundle()
            bundle.putString(MENU_STATE_KEY, HIDE_MENU_KEY)
            intent.putExtras(bundle)
            context.startService(intent)
        }
    }
}
