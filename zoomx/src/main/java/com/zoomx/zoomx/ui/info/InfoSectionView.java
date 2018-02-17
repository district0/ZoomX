package com.zoomx.zoomx.ui.info;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.InfoModel;

import java.util.List;

/**
 * Created by Ahmed Fathallah on 2/11/2018.
 */

public class InfoSectionView extends FrameLayout {


    private LinearLayout baseView;
    private TextView sectionTitleView;

    public InfoSectionView(@NonNull Context context) {
        super(context);
        initUI();
    }

    public InfoSectionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public InfoSectionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        View view = inflate(getContext(), R.layout.view_info_section, this);
        baseView = view.findViewById(R.id.view_info_section);
        sectionTitleView = baseView.findViewById(R.id.info_section_title);
    }

    public void setSectionTitle(String title) {
        sectionTitleView.setText(title);
    }

    public void setInfoModels(List<InfoModel> infoModels) {
        if (infoModels == null)
            return;
        for (InfoModel model : infoModels) {
            if (model.isTextViewType()) {
                baseView.addView(createViewWithText(model));
            } else if (model.isButtonType()) {
                baseView.addView(createViewWithButton(model));
            } else if (model.isSwitchType()) {
                baseView.addView(createViewWithSwitch(model));
            }
        }
    }

    private View createViewWithText(InfoModel model) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.info_text_view, null);
        TextView title = view.findViewById(R.id.info_text_title_tv);
        TextView value = view.findViewById(R.id.info_text_value_tv);
        title.setText(model.getTitle());
        value.setText(model.getValue());
        return view;
    }

    private View createViewWithSwitch(InfoModel model) {
        return null;
    }

    private View createViewWithButton(InfoModel model) {
        return null;
    }

}
