package com.jkrm.education.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzw on 2019/5/31.
 */

public class VideoResultBean implements Serializable{


    /**
     * cataVideos : [{"accessUrl":"https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/knowledge/video-djwk-yls-4.mp4","contentType":"video/mpeg4","
     * createBy":"","createTime":"2019-05-31T09:34:07+0800","deleteStatus":"0","fileExt":"mp4","fileSize":"40999322",
     * "filename":"对点微课第4题.mp4","id":"video005","remark":"","updateBy":"","updateTime":"2019-05-31T09:55:51+0800"},
     * {"accessUrl":"https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/knowledge/video-djwk-yls-16.mp4",
     * "contentType":"video/mpeg4","createBy":"","createTime":"2019-05-31T09:34:10+0800","deleteStatus":"0","fileExt":"mp4","fileSize":"49702503",
     * "filename":"对点微课第16题用正负数表示具有相反意义的量.mp4","id":"video006","remark":"","updateBy":"","updateTime":"2019-05-31T09:56:15+0800"},{"accessUrl":"https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/knowledge/video-djwk-yls-17.mp4","contentType":"video/mpeg4","createBy":"","createTime":"2019-05-31T09:34:14+0800","deleteStatus":"0","fileExt":"mp4","fileSize":"52323943","filename":"对点微课第17题正负数的实际应用.mp4","id":"video007","remark":"","updateBy":"","updateTime":"2019-05-31T09:56:38+0800"}]
     *
     *  questionVideo : {"accessUrl":"https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/video-msjt-yls-4.mp4",
     * "contentType":"video/mpeg4","createBy":"","createTime":"2019-05-31T09:28:14+0800","deleteStatus":"0",
     * "fileExt":"mp4","fileSize":"57147392","filename":"名师讲题第4题.mp4","id":"video001","remark":"","updateBy":"","updateTime":"2019-05-31T09:42:49+0800"}
     */

    private QuestionVideoBean questionVideo;
    private List<CataVideosBean> cataVideos;

    public QuestionVideoBean getQuestionVideo() {
        return questionVideo;
    }

    public void setQuestionVideo(QuestionVideoBean questionVideo) {
        this.questionVideo = questionVideo;
    }

    public List<CataVideosBean> getCataVideos() {
        return cataVideos;
    }

    public void setCataVideos(List<CataVideosBean> cataVideos) {
        this.cataVideos = cataVideos;
    }

    public static class QuestionVideoBean  implements Serializable{
        /**
         * accessUrl : https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/video-msjt-yls-4.mp4
         * contentType : video/mpeg4
         * createBy :
         * createTime : 2019-05-31T09:28:14+0800
         * deleteStatus : 0
         * fileExt : mp4
         * fileSize : 57147392
         * filename : 名师讲题第4题.mp4
         * id : video001
         * remark :
         * updateBy :
         * updateTime : 2019-05-31T09:42:49+0800
         */

        private String accessUrl;
        private String contentType;
        private String createBy;
        private String createTime;
        private String deleteStatus;
        private String fileExt;
        private String fileSize;
        private String filename;
        private String id;
        private String remark;
        private String updateBy;
        private String updateTime;

        public String getAccessUrl() {
            return accessUrl;
        }

        public void setAccessUrl(String accessUrl) {
            this.accessUrl = accessUrl;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getFileExt() {
            return fileExt;
        }

        public void setFileExt(String fileExt) {
            this.fileExt = fileExt;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class CataVideosBean  implements Serializable{
        /**
         * accessUrl : https://jbyadmin.oss-cn-beijing.aliyuncs.com/jby_files/prod/videos/knowledge/video-djwk-yls-4.mp4
         * contentType : video/mpeg4
         * createBy :
         * createTime : 2019-05-31T09:34:07+0800
         * deleteStatus : 0
         * fileExt : mp4
         * fileSize : 40999322
         * filename : 对点微课第4题.mp4
         * id : video005
         * remark :
         * updateBy :
         * updateTime : 2019-05-31T09:55:51+0800
         */

        private String accessUrl;
        private String contentType;
        private String createBy;
        private String createTime;
        private String deleteStatus;
        private String fileExt;
        private String fileSize;
        private String filename;
        private String id;
        private String remark;
        private String updateBy;
        private String updateTime;

        public String getAccessUrl() {
            return accessUrl;
        }

        public void setAccessUrl(String accessUrl) {
            this.accessUrl = accessUrl;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getFileExt() {
            return fileExt;
        }

        public void setFileExt(String fileExt) {
            this.fileExt = fileExt;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
