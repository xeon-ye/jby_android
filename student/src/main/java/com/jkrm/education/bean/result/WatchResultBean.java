package com.jkrm.education.bean.result;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/1 14:33
 */

public class WatchResultBean {

    /**
     * code : 200
     * data : {"finishedList":[{"createBy":"","createTime":"2020-04-01T16:47:33+0800","deleteStatus":"0","goodsId":"1021","goodsName":"yy的套餐","goodsUrl":"http://img.xinjiaoyu.com/jby_files/test/videos/B4AE2700F1584F118A846A831D87C779.png","id":"7db94c64193bd5f603f8d1bb7da07c25","module":"video","msg":"","remark":"","totalTime":"434","updateBy":"","updateTime":"2020-04-01T16:47:33+0800","userId":"e7dbfd6c397f365ff5d50903cd216c5c","videoId":"10003","videoTime":"25","watchStatus":"0","watchTime":"14"}],"unfinishedList":[{"createBy":"","createTime":"2020-04-01T16:47:33+0800","deleteStatus":"0","goodsId":"1021","goodsName":"yy的套餐","goodsUrl":"http://img.xinjiaoyu.com/jby_files/test/videos/B4AE2700F1584F118A846A831D87C779.png","id":"7db94c64193bd5f603f8d1bb7da07c25","module":"video","msg":"","remark":"","totalTime":"434","updateBy":"","updateTime":"2020-04-01T16:47:33+0800","userId":"e7dbfd6c397f365ff5d50903cd216c5c","videoId":"10003","videoTime":"25","watchStatus":"0","watchTime":"14"}]}
     * msg :
     */



        private List<FinishedListBean> finishedList;
        private List<UnfinishedListBean> unfinishedList;

        public List<FinishedListBean> getFinishedList() {
            return finishedList;
        }

        public void setFinishedList(List<FinishedListBean> finishedList) {
            this.finishedList = finishedList;
        }

        public List<UnfinishedListBean> getUnfinishedList() {
            return unfinishedList;
        }

        public void setUnfinishedList(List<UnfinishedListBean> unfinishedList) {
            this.unfinishedList = unfinishedList;
        }

        public static class FinishedListBean {
            /**
             * createBy :
             * createTime : 2020-04-01T16:47:33+0800
             * deleteStatus : 0
             * goodsId : 1021
             * goodsName : yy的套餐
             * goodsUrl : http://img.xinjiaoyu.com/jby_files/test/videos/B4AE2700F1584F118A846A831D87C779.png
             * id : 7db94c64193bd5f603f8d1bb7da07c25
             * module : video
             * msg :
             * remark :
             * totalTime : 434
             * updateBy :
             * updateTime : 2020-04-01T16:47:33+0800
             * userId : e7dbfd6c397f365ff5d50903cd216c5c
             * videoId : 10003
             * videoTime : 25
             * watchStatus : 0
             * watchTime : 14
             */

            private String createBy;
            private String createTime;
            private String deleteStatus;
            private String goodsId;
            private String goodsName;
            private String goodsUrl;
            private String id;
            private String module;
            private String msg;
            private String remark;
            private String totalTime;
            private String updateBy;
            private String updateTime;
            private String userId;
            private String videoId;
            private String videoTime;
            private String watchStatus;
            private String watchTime;

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

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsUrl() {
                return goodsUrl;
            }

            public void setGoodsUrl(String goodsUrl) {
                this.goodsUrl = goodsUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(String totalTime) {
                this.totalTime = totalTime;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getVideoTime() {
                return videoTime;
            }

            public void setVideoTime(String videoTime) {
                this.videoTime = videoTime;
            }

            public String getWatchStatus() {
                return watchStatus;
            }

            public void setWatchStatus(String watchStatus) {
                this.watchStatus = watchStatus;
            }

            public String getWatchTime() {
                return watchTime;
            }

            public void setWatchTime(String watchTime) {
                this.watchTime = watchTime;
            }
        }

        public static class UnfinishedListBean {
            /**
             * createBy :
             * createTime : 2020-04-01T16:47:33+0800
             * deleteStatus : 0
             * goodsId : 1021
             * goodsName : yy的套餐
             * goodsUrl : http://img.xinjiaoyu.com/jby_files/test/videos/B4AE2700F1584F118A846A831D87C779.png
             * id : 7db94c64193bd5f603f8d1bb7da07c25
             * module : video
             * msg :
             * remark :
             * totalTime : 434
             * updateBy :
             * updateTime : 2020-04-01T16:47:33+0800
             * userId : e7dbfd6c397f365ff5d50903cd216c5c
             * videoId : 10003
             * videoTime : 25
             * watchStatus : 0
             * watchTime : 14
             */

            private String createBy;
            private String createTime;
            private String deleteStatus;
            private String goodsId;
            private String goodsName;
            private String goodsUrl;
            private String id;
            private String module;
            private String msg;
            private String remark;
            private String totalTime;
            private String updateBy;
            private String updateTime;
            private String userId;
            private String videoId;
            private String videoTime;
            private String watchStatus;
            private String watchTime;

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

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsUrl() {
                return goodsUrl;
            }

            public void setGoodsUrl(String goodsUrl) {
                this.goodsUrl = goodsUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(String totalTime) {
                this.totalTime = totalTime;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getVideoTime() {
                return videoTime;
            }

            public void setVideoTime(String videoTime) {
                this.videoTime = videoTime;
            }

            public String getWatchStatus() {
                return watchStatus;
            }

            public void setWatchStatus(String watchStatus) {
                this.watchStatus = watchStatus;
            }

            public String getWatchTime() {
                return watchTime;
            }

            public void setWatchTime(String watchTime) {
                this.watchTime = watchTime;
            }
        }
}
