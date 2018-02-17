package com.zoomx.zoomx.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.InfoModel;
import com.zoomx.zoomx.util.PhoneUtils;

import java.util.ArrayList;

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

public class InfoFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_fragment, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        ((InfoActivity) getActivity()).getSupportActionBar().setTitle(R.string.info_screen_title);

        InfoSectionView appInfo = view.findViewById(R.id.info_app_view);
        appInfo.setInfoModels(createAppInfo());
        appInfo.setSectionTitle(getString(R.string.info_app_section_title));

        InfoSectionView deviceInfo = view.findViewById(R.id.info_device_view);
        deviceInfo.setInfoModels(createDeviceInfo());
        deviceInfo.setSectionTitle(getString(R.string.info_device_section_title));
    }

    private ArrayList<InfoModel> createDeviceInfo() {
        ArrayList<InfoModel> infoModels = new ArrayList<>();
        InfoModel model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_model), PhoneUtils.getDeviceModel());
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_density), PhoneUtils.getDensity(getContext()));
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_device_resolution), PhoneUtils.getDeviceResolution(getContext()));
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_release), PhoneUtils.getAndroidRelease());
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_api), String.valueOf(PhoneUtils.getAndroidApi()));
        infoModels.add(model);
        return infoModels;
    }

    private ArrayList<InfoModel> createAppInfo() {
        ArrayList<InfoModel> infoModels = new ArrayList<>();
        InfoModel model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_package_name), PhoneUtils.getPackageName(getContext()));
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_version_name), PhoneUtils.getAppVersionName(getContext()));
        infoModels.add(model);
        model = new InfoModel(InfoModel.TEXT_VIEW_TYPE, getString(R.string.info_version_code), String.valueOf(PhoneUtils.getAppVersion(getContext())));
        infoModels.add(model);
        return infoModels;
    }
}
