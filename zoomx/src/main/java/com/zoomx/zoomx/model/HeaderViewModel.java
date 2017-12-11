package com.zoomx.zoomx.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Fathallah on 11/20/2017.
 */
public class HeaderViewModel {

    private Map<String, String> headersMap;

    public HeaderViewModel() {
        this.headersMap = new HashMap<>();
    }

    public HeaderViewModel(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public void addHeader(String name, String value) {
        headersMap.put(name, value);
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }
}
