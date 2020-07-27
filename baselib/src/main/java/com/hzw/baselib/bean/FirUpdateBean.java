package com.hzw.baselib.bean;

/**
 * Created by hzw on 2019/4/8.
 */

public class FirUpdateBean {

    public static final String URL = "http://api.fir.im/apps/latest/";
    public static final String API_TEACHER_ID = "5cece292959d6908a8fadcc1";
    public static final String API_STUDENT_ID = "5cece361548b7a756e824ddd";
    public static final String API_TOKEN = "e3640f25fefc9e192e3730574077d043";

    private String name;
    private String version;
    private String changelog;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String update_url;
    private Object binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public Object getBinary() {
        return binary;
    }

    public void setBinary(Object binary) {
        this.binary = binary;
    }

    @Override
    public String toString() {
        return "FirUpdateBean{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", changelog='" + changelog + '\'' +
                ", versionShort='" + versionShort + '\'' +
                ", build='" + build + '\'' +
                ", installUrl='" + installUrl + '\'' +
                ", install_url='" + install_url + '\'' +
                ", update_url='" + update_url + '\'' +
                ", binary=" + binary +
                '}';
    }
}
