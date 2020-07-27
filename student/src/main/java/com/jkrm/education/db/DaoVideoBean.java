package com.jkrm.education.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.jkrm.education.db.greendao.DaoSession;
import com.jkrm.education.db.greendao.DaoCatalogueBeanDao;
import com.jkrm.education.db.greendao.DaoVideoBeanDao;

/**
 * @Description: 微课子条目视频
 * @Author: xiangqian
 * @CreateDate: 2020/3/17 15:07
 */

@Entity
public class DaoVideoBean {
    @Id
    private String id;
    private String brief;
    private String catalogId;
    private String face;
    private String faceId;
    private String ids;
    private String mainCourse;
    private String mlessonId;
    private String name;
    private String oneId;
    private String pcvId;
    private String price;
    private String size;
    private String teacherId;
    private String teacherName;
    private String times;
    private String twoId;
    private String type;
    private String updateTime;
    private String url;
    private String videoId;
    private String whetherBuy;
    private String whetherFree;


    /**
     * 下载状态
     */
    public static final String DOWNLOAD = "download";    // 下载中
    public static final String DOWNLOAD_PAUSE = "pause"; // 下载暂停
    public static final String DOWNLOAD_WAIT = "wait";  // 等待下载
    public static final String DOWNLOAD_CANCEL = "cancel"; // 下载取消
    public static final String DOWNLOAD_OVER = "over";    // 下载结束
    public static final String DOWNLOAD_ERROR = "error";  // 下载出错

    public static final long TOTAL_ERROR = -1;//获取进度失败

    private String fileName;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String downloadStatus="";
    private long total;
    private long progress;
    private boolean isCheck;
    @Generated(hash = 308259790)
    public DaoVideoBean(String id, String brief, String catalogId, String face,
            String faceId, String ids, String mainCourse, String mlessonId,
            String name, String oneId, String pcvId, String price, String size,
            String teacherId, String teacherName, String times, String twoId,
            String type, String updateTime, String url, String videoId,
            String whetherBuy, String whetherFree, String fileName, String filePath,
            String downloadStatus, long total, long progress, boolean isCheck) {
        this.id = id;
        this.brief = brief;
        this.catalogId = catalogId;
        this.face = face;
        this.faceId = faceId;
        this.ids = ids;
        this.mainCourse = mainCourse;
        this.mlessonId = mlessonId;
        this.name = name;
        this.oneId = oneId;
        this.pcvId = pcvId;
        this.price = price;
        this.size = size;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.times = times;
        this.twoId = twoId;
        this.type = type;
        this.updateTime = updateTime;
        this.url = url;
        this.videoId = videoId;
        this.whetherBuy = whetherBuy;
        this.whetherFree = whetherFree;
        this.fileName = fileName;
        this.filePath = filePath;
        this.downloadStatus = downloadStatus;
        this.total = total;
        this.progress = progress;
        this.isCheck = isCheck;
    }

    @Generated(hash = 1149939450)
    public DaoVideoBean() {
    }
    public String getBrief() {
        return this.brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public String getCatalogId() {
        return this.catalogId;
    }
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    public String getFace() {
        return this.face;
    }
    public void setFace(String face) {
        this.face = face;
    }
    public String getFaceId() {
        return this.faceId;
    }
    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIds() {
        return this.ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    public String getMainCourse() {
        return this.mainCourse;
    }
    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }
    public String getMlessonId() {
        return this.mlessonId;
    }
    public void setMlessonId(String mlessonId) {
        this.mlessonId = mlessonId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOneId() {
        return this.oneId;
    }
    public void setOneId(String oneId) {
        this.oneId = oneId;
    }
    public String getPcvId() {
        return this.pcvId;
    }
    public void setPcvId(String pcvId) {
        this.pcvId = pcvId;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getSize() {
        return this.size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getTeacherId() {
        return this.teacherId;
    }
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    public String getTeacherName() {
        return this.teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTimes() {
        return this.times;
    }
    public void setTimes(String times) {
        this.times = times;
    }
    public String getTwoId() {
        return this.twoId;
    }
    public void setTwoId(String twoId) {
        this.twoId = twoId;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getVideoId() {
        return this.videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getWhetherBuy() {
        return this.whetherBuy;
    }
    public void setWhetherBuy(String whetherBuy) {
        this.whetherBuy = whetherBuy;
    }
    public String getWhetherFree() {
        return this.whetherFree;
    }
    public void setWhetherFree(String whetherFree) {
        this.whetherFree = whetherFree;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getDownloadStatus() {
        return this.downloadStatus;
    }
    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
    public long getTotal() {
        return this.total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public long getProgress() {
        return this.progress;
    }
    public void setProgress(long progress) {
        this.progress = progress;
    }
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

}
