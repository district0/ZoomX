package com.zoomx.zoomx.ui.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoomx.zoomx.R
import com.zoomx.zoomx.model.InfoModel
import com.zoomx.zoomx.util.PhoneUtils
import java.util.*

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

class InfoFragment : Fragment() {

    private var view: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.info_fragment, container, false)
        initUI()
        return view
    }

    private fun initUI() {
        (activity as InfoActivity).supportActionBar!!.setTitle(R.string.info_screen_title)

        val appInfo = view!!.findViewById<InfoSectionView>(R.id.info_app_view)
        appInfo.setInfoModels(createAppInfo())
        appInfo.setSectionTitle(getString(R.string.info_app_section_title))

        val deviceInfo = view!!.findViewById<InfoSectionView>(R.id.info_device_view)
        deviceInfo.setInfoModels(createDeviceInfo())
        deviceInfo.setSectionTitle(getString(R.string.info_device_section_title))

        val actions = view!!.findViewById<InfoSectionView>(R.id.info_actions_view)
        actions.setInfoModels(createSettings())
        actions.setSectionTitle(getString(R.string.info_settings_section_title))
    }

    private fun createSettings(): ArrayList<InfoModel> {
        val infoModels = ArrayList<InfoModel>()
        var model = InfoModel(InfoModel.BUTTON_VIEW_TYPE, getString(R.string.info_clear_app_data), null!!, Runnable { PhoneUtils.clearAppData(activity) })
        infoModels.add(model)
        model = InfoModel(InfoModel.BUTTON_VIEW_TYPE, getString(R.string.info_uninstall), null!!, Runnable { PhoneUtils.uninstall(activity) })
        infoModels.add(model)
        return infoModels
    }


    private fun createDeviceInfo(): ArrayList<InfoModel> {
        val infoModels = ArrayList<InfoModel>()
        var model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_model), PhoneUtils.deviceModel)
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_density), PhoneUtils.getDensity(context))
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_resolution), PhoneUtils.getDeviceResolution(context))
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_release), PhoneUtils.androidRelease)
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_api), PhoneUtils.androidApi.toString())
        infoModels.add(model)
        return infoModels
    }

    private fun createAppInfo(): ArrayList<InfoModel> {
        val infoModels = ArrayList<InfoModel>()
        var model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_package_name), PhoneUtils.getPackageName(context))
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_version_name), PhoneUtils.getAppVersionName(context))
        infoModels.add(model)
        model = InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_version_code), PhoneUtils.getAppVersion(context).toString())
        infoModels.add(model)
        return infoModels
    }
}
