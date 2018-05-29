package com.zoomx.zoomx.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.ImageView

import com.zoomx.zoomx.R
import com.zoomx.zoomx.ui.info.InfoActivity
import com.zoomx.zoomx.ui.requestlist.RequestActivity
import com.zoomx.zoomx.ui.settings.SettingActivity

/**
 * Created by Ahmed Fathallah on 12/13/2017.
 */

class MainActionMenu(context: Context, private val menuEventsListener: ActionMenuEventsListener?) : FrameLayout(context), View.OnClickListener {

    internal var isScrolling = false
    private var menuButton: ImageView? = null
    private var expandedView: View? = null
    var isMenuOpened = false
        private set
    private val initialTouchX: Float = 0.toFloat()
    private val initialTouchY: Float = 0.toFloat()
    private var startX: Float = 0.toFloat()
    private var startY: Float = 0.toFloat()
    private var prevX: Float = 0.toFloat()
    private var prevY: Float = 0.toFloat()
    private var mTouchSlop: Int = 0

    init {
        initUI(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private fun initUI(context: Context) {
        val view = View.inflate(context, R.layout.view_main_actions_menu, this)
        menuButton = view.findViewById(R.id.menu_main_fab)
        val dismissButton = view.findViewById<ImageView>(R.id.menu_dismiss_fab)
        val settingsButton = view.findViewById<ImageView>(R.id.menu_settings_fab)
        val featuresButton = view.findViewById<ImageView>(R.id.menu_features_fab)
        val infoButton = view.findViewById<ImageView>(R.id.menu_info_fab)
        expandedView = view.findViewById(R.id.menu_expanded_view)

        menuButton!!.setOnClickListener(this)
        dismissButton.setOnClickListener(this)
        settingsButton.setOnClickListener(this)
        featuresButton.setOnClickListener(this)
        infoButton.setOnClickListener(this)

        val mViewConfiguration = ViewConfiguration.get(getContext())
        mTouchSlop = mViewConfiguration.scaledTouchSlop
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.menu_main_fab) {
            expand()
        } else if (id == R.id.menu_dismiss_fab) {
            collapse()
        } else if (id == R.id.menu_settings_fab) {
            val intent = Intent(context, SettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else if (id == R.id.menu_features_fab) {
            val intent = Intent(context, RequestActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else if (id == R.id.menu_info_fab) {
            val intent = Intent(context, InfoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun collapse() {
        expandedView!!.visibility = View.GONE
        menuButton!!.visibility = View.VISIBLE
        isMenuOpened = false
    }

    fun expand() {
        expandedView!!.visibility = View.VISIBLE
        menuButton!!.visibility = View.GONE
        isMenuOpened = true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val action = event.action

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isScrolling = false
            return false
        }

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                startY = event.rawY
                prevX = startX
                prevY = startY
                isScrolling = false
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                if (isScrolling)
                    return true

                val Xdiff = (event.rawX - startX).toInt()
                val Ydiff = (event.rawY - startY).toInt()
                if (Math.abs(Ydiff) > mTouchSlop || Math.abs(Xdiff) > mTouchSlop) {
                    // Start scrolling!
                    isScrolling = true
                    return true
                }
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currX = event.rawX
        val currY = event.rawY
        when (event.action) {
        //            case MotionEvent.ACTION_DOWN:
        //                startX = currX;
        //                startY = currY;
        //                prevX  = currX;
        //                prevY  = currY;
        //                break;
            MotionEvent.ACTION_MOVE -> if (menuEventsListener != null) {
                val dX = currX - prevX
                val dY = currY - prevY
                prevX = currX
                prevY = currY
                menuEventsListener.OnMenuMoved(dX, dY)
            }
        }
        return true
    }


    interface ActionMenuEventsListener {
        fun OnMenuMoved(dx: Float, dy: Float)
    }
}
