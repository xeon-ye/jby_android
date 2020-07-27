package com.jkrm.education.bean.rx;

import java.util.Map;

/**
 * Created by hzw on 2019/6/16.
 */

public class RxAlipushDataResultType {

    private String title;
    private String content;
    private Map<String, String> extraMap;

    public RxAlipushDataResultType(String title, String content,  Map<String, String> extraMap) {
        this.title = title;
        this.content = content;
        this.extraMap = extraMap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, String> extraMap) {
        this.extraMap = extraMap;
    }
}
