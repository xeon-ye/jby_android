package com.hzw.baselib.bean;

/**
 * Created by hzw on 2019/4/9.
 */

public class FirUpdateResultBean {


    /**
     * name : 阿律法务
     * version : 1
     * changelog : 测试环境初版1.0.1
     * updated_at : 1554191303
     * versionShort : 1.0.1
     * build : 1
     * installUrl : http://download.fir.im/apps/5ca3139e548b7a6c59f9ba54/install?download_token=190f4339ad9b3123521a00d36297ea82&source=update
     * install_url : http://download.fir.im/apps/5ca3139e548b7a6c59f9ba54/install?download_token=190f4339ad9b3123521a00d36297ea82&source=update
     * direct_install_url : http://download.fir.im/apps/5ca3139e548b7a6c59f9ba54/install?download_token=190f4339ad9b3123521a00d36297ea82&source=update
     * update_url : http://fir.im/y9l4
     * binary : {"fsize":6257177}
     */

    private Object name;
    private Object version;
    private Object changelog;
    private Object updated_at;
    private Object versionShort;
    private Object build;
    private Object installUrl;
    private Object install_url;
    private Object direct_install_url;
    private Object update_url;
    private Object binary;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getChangelog() {
        return changelog;
    }

    public void setChangelog(Object changelog) {
        this.changelog = changelog;
    }

    public Object getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    public Object getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(Object versionShort) {
        this.versionShort = versionShort;
    }

    public Object getBuild() {
        return build;
    }

    public void setBuild(Object build) {
        this.build = build;
    }

    public Object getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(Object installUrl) {
        this.installUrl = installUrl;
    }

    public Object getInstall_url() {
        return install_url;
    }

    public void setInstall_url(Object install_url) {
        this.install_url = install_url;
    }

    public Object getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(Object direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public Object getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(Object update_url) {
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
        return "FirUpdateResultBean{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", changelog='" + changelog + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", versionShort='" + versionShort + '\'' +
                ", build='" + build + '\'' +
                ", installUrl='" + installUrl + '\'' +
                ", install_url='" + install_url + '\'' +
                ", direct_install_url='" + direct_install_url + '\'' +
                ", update_url='" + update_url + '\'' +
                ", binary=" + binary +
                '}';
    }
}
