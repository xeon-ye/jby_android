package com.jkrm.education.bean.scan;

/**
 * 扫描信息
 * Created by hzw on 2019/5/14.
 */

public class ScanBean {

    private String classes;
    private int totalScanPage;
    private int ScanedPage;

    public ScanBean(String classes, int totalScanPage, int scanedPage) {
        this.classes = classes;
        this.totalScanPage = totalScanPage;
        ScanedPage = scanedPage;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public int getTotalScanPage() {
        return totalScanPage;
    }

    public void setTotalScanPage(int totalScanPage) {
        this.totalScanPage = totalScanPage;
    }

    public int getScanedPage() {
        return ScanedPage;
    }

    public void setScanedPage(int scanedPage) {
        ScanedPage = scanedPage;
    }
}
