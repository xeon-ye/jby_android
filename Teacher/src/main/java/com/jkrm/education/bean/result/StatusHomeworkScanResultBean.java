package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/11.
 */

public class StatusHomeworkScanResultBean {


    /**
     * missing : 56
     * population : 240
     * progress : 184
     * submitted : 184
     * unprogress : 0
     */

    private String missing;
    private String population;
    private String progress;
    private String submitted;
    private String unprogress;

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getUnprogress() {
        return unprogress;
    }

    public void setUnprogress(String unprogress) {
        this.unprogress = unprogress;
    }
}
