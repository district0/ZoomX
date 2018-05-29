package com.zoomx.zoomx.ui.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.InfoModel

/**
 * Created by Ahmed Fathallah on 2/11/2018.
 */

class InfoSectionView : FrameLayout {


    private var baseView: LinearLayout? = null
    private var sectionTitleView: TextView? = null

    constructor(context: Context) : super(context) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI()
    }

    private fun initUI() {
        val view = View.inflate(context, R.layout.view_info_section, this)
        baseView = view.findViewById(R.id.view_info_section)
        sectionTitleView = baseView!!.findViewById(R.id.info_section_title)
    }

    fun setSectionTitle(title: String) {
        sectionTitleView!!.text = title
    }

    fun setInfoModels(infoModels: List<InfoModel>?) {
        if (infoModels == null)
            return
        for (model in infoModels) {
            if (model.isTextViewType) {
                baseView!!.addView(createViewWithText(model))
            } else if (model.isButtonType) {
                baseView!!.addView(createViewWithButton(model))
            } else if (model.isSwitchType) {
                baseView!!.addView(createViewWithSwitch(model))
            }
        }
    }

    private fun createViewWithText(model: InfoModel): View {
        val view = LayoutInflater.from(context).inflate(R.layout.info_text_view, null)
        val title = view.findViewById<TextView>(R.id.info_text_title_tv)
        val value = view.findViewById<TextView>(R.id.info_text_value_tv)
        title.text = model.title
        value.text = model.value
        return view
    }

    private fun createViewWithButton(model: InfoModel): View {
        val view = LayoutInflater.from(context).inflate(R.layout.info_text_view, null)
        val title = view.findViewById<TextView>(R.id.info_text_title_tv)
        val value = view.findViewById<TextView>(R.id.info_text_value_tv)
        title.text = model.title
        value.visibility = View.GONE
        view.setOnClickListener {
            if (model.buttonAction != null) {
                model.buttonAction!!.run()
            }
        }
        return view
    }

    private fun createViewWithSwitch(model: InfoModel): View? {
        return null
    }

}
