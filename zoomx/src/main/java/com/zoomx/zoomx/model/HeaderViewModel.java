package com.zoomx.zoomx.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */
public class HeaderViewModel {

    private ArrayList<String> headersMap;

    public HeaderViewModel() {
        this.headersMap = new ArrayList<>();
    }

    public HeaderViewModel(ArrayList<String> headersMap) {
        this.headersMap = headersMap;
    }

    public void addHeader(String name, String value) {
        headersMap.add(name + ": " + value);
    }

    public ArrayList<String> getHeadersMap() {
        return headersMap;
    }
}
