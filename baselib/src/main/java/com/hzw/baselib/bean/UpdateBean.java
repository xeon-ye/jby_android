package com.hzw.baselib.bean;

/**
 * Created by hzw on 2017/11/29.
 */

public class UpdateBean {

    private String version;    // 版本号
    private String url;     // 下载地址
    private String updateContent;   // 更新内容

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", updateContent='" + updateContent + '\'' +
                '}';
    }
}
