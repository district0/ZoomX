package com.zoomx.zoomx.model;

/**
 * Created by Ahmed Fathallah on 2/15/2018.
 */

public class InfoModel {
    public static final int TEXT_VIEW_TYPE = 601;
    public static final int SWITCH_VIEW_TYPE = 602;
    public static final int BUTTON_VIEW_TYPE = 603;

    private String title;
    private int type;
    private String value;
    private boolean state;
    private Runnable buttonAction;

    public InfoModel(int type, String title, String value) {
        this.title = title;
        this.type = type;
        this.value = value;
        this.buttonAction = null;
    }

    public InfoModel(int type, String title, String value, Runnable buttonAction) {
        this.title = title;
        this.type = type;
        this.value = value;
        this.buttonAction = buttonAction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isTextViewType() {
        return TEXT_VIEW_TYPE == type;
    }

    public boolean isSwitchType() {
        return SWITCH_VIEW_TYPE == type;
    }

    public boolean isButtonType() {
        return BUTTON_VIEW_TYPE == type;
    }

    public Runnable getButtonAction() {
        return buttonAction;
    }
}
