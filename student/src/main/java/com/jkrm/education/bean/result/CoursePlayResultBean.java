package com.jkrm.education.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/16 15:48
 */

public class CoursePlayResultBean implements Serializable {


        /**
         * children : []
         * id : 1b170792c202db319ca9adaca41e766b
         * key : 1b170792c202db319ca9adaca41e766b
         * mlessonId : 1021
         * name : yy-第一章
         * parentId : 0
         * pcvId : c26c325c00c4fad2fb95658039876401,6d0005e737f6890a434ac68dd7ae9a51,2abad4627d44c059a599dcd55b40869b,9b934e7cfbcf0de1f4eed293c7c547b7
         * title : yy-第一章
         * videoList : [{"brief":"","catalogId":"1b170792c202db319ca9adaca41e766b","face":"http://img.xinjiaoyu.com/jby_files/prod/videos/6A18E822262748778F60BDE0B70861BF.jpg","faceId":"9825c3a15ab6649a48372a722a1df46e","id":"1024","ids":"","mainCourse":"2abad4627d44c059a599dcd55b40869b","mlessonId":"1021","name":"负数和0的认识","oneId":"","pcvId":"c26c325c00c4fad2fb95658039876401,6d0005e737f6890a434ac68dd7ae9a51,2abad4627d44c059a599dcd55b40869b,9b934e7cfbcf0de1f4eed293c7c547b7","price":"0.0","size":"","teacherId":"","teacherName":"丁老师","times":"07:14","twoId":"","type":"1","updateTime":"2020-03-12T18:01:53+0800","url":"http://dist.xinjiaoyu.com/videos/points/A774CE0B312D4DD299F6AC106BBDCFAC.mp4","videoId":"10003","whetherBuy":"0","whetherFree":"0"},{"brief":"","catalogId":"1b170792c202db319ca9adaca41e766b","face":"http://img.xinjiaoyu.com/jby_files/prod/videos/5E5289D4FB0F43E995904CE68707CF15.jpg","faceId":"4ebef2ffce987c0bb56f3f92357c4f4f","id":"1023","ids":"","mainCourse":"2abad4627d44c059a599dcd55b40869b","mlessonId":"1021","name":"用正负数表示具有相反意义的量","oneId":"","pcvId":"c26c325c00c4fad2fb95658039876401,6d0005e737f6890a434ac68dd7ae9a51,2abad4627d44c059a599dcd55b40869b,9b934e7cfbcf0de1f4eed293c7c547b7","price":"0.0","size":"","teacherId":"","teacherName":"丁老师","times":"06:45","twoId":"","type":"1","updateTime":"2020-03-12T18:01:53+0800","url":"http://dist.xinjiaoyu.com/videos/points/BA7F314C62C340F5A3C486A44EA5D870.mp4","videoId":"10004","whetherBuy":"0","whetherFree":"0"},{"brief":"","catalogId":"1b170792c202db319ca9adaca41e766b","face":"http://img.xinjiaoyu.com/jby_files/prod/videos/9592D854550D4F7CB392CC1DE1842D55.jpg","faceId":"0666471c1936b712f9321b9c2e9811ca","id":"1022","ids":"","mainCourse":"2abad4627d44c059a599dcd55b40869b","mlessonId":"1021","name":"正负数的实际应用","oneId":"","pcvId":"c26c325c00c4fad2fb95658039876401,6d0005e737f6890a434ac68dd7ae9a51,2abad4627d44c059a599dcd55b40869b,9b934e7cfbcf0de1f4eed293c7c547b7","price":"0.0","size":"","teacherId":"","teacherName":"丁老师","times":"06:53","twoId":"","type":"1","updateTime":"2020-03-12T18:01:53+0800","url":"http://dist.xinjiaoyu.com/videos/points/AB4656B252294E19A0E526B33BE92B9C.mp4","videoId":"10007","whetherBuy":"0","whetherFree":"0"}]
         */

        private String id;
        private String key;
        private String mlessonId;
        private String name;
        private String parentId;
        private String pcvId;
        private String title;
        private List<?> children;
        private List<VideoListBean> videoList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMlessonId() {
            return mlessonId;
        }

        public void setMlessonId(String mlessonId) {
            this.mlessonId = mlessonId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPcvId() {
            return pcvId;
        }

        public void setPcvId(String pcvId) {
            this.pcvId = pcvId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        public static class VideoListBean implements Serializable{
            /**
             * brief :
             * catalogId : 1b170792c202db319ca9adaca41e766b
             * face : http://img.xinjiaoyu.com/jby_files/prod/videos/6A18E822262748778F60BDE0B70861BF.jpg
             * faceId : 9825c3a15ab6649a48372a722a1df46e
             * id : 1024
             * ids :
             * mainCourse : 2abad4627d44c059a599dcd55b40869b
             * mlessonId : 1021
             * name : 负数和0的认识
             * oneId :
             * pcvId : c26c325c00c4fad2fb95658039876401,6d0005e737f6890a434ac68dd7ae9a51,2abad4627d44c059a599dcd55b40869b,9b934e7cfbcf0de1f4eed293c7c547b7
             * price : 0.0
             * size :
             * teacherId :
             * teacherName : 丁老师
             * times : 07:14
             * twoId :
             * type : 1
             * updateTime : 2020-03-12T18:01:53+0800
             * url : http://dist.xinjiaoyu.com/videos/points/A774CE0B312D4DD299F6AC106BBDCFAC.mp4
             * videoId : 10003
             * whetherBuy : 0
             * whetherFree : 0
             */

            private String brief;
            private String catalogId;
            private String face;
            private String faceId;
            private String id;
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
            private boolean isChecked;
            private int watchTime;
            private String watchStatus;
            private boolean isPlaying;

            public boolean isPlaying() {
                return isPlaying;
            }

            public void setPlaying(boolean playing) {
                isPlaying = playing;
            }

            public int getWatchTime() {
                return watchTime;
            }

            public void setWatchTime(int watchTime) {
                this.watchTime = watchTime;
            }

            public String getWatchStatus() {
                return watchStatus;
            }

            public void setWatchStatus(String watchStatus) {
                this.watchStatus = watchStatus;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getCatalogId() {
                return catalogId;
            }

            public void setCatalogId(String catalogId) {
                this.catalogId = catalogId;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getFaceId() {
                return faceId;
            }

            public void setFaceId(String faceId) {
                this.faceId = faceId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getMainCourse() {
                return mainCourse;
            }

            public void setMainCourse(String mainCourse) {
                this.mainCourse = mainCourse;
            }

            public String getMlessonId() {
                return mlessonId;
            }

            public void setMlessonId(String mlessonId) {
                this.mlessonId = mlessonId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOneId() {
                return oneId;
            }

            public void setOneId(String oneId) {
                this.oneId = oneId;
            }

            public String getPcvId() {
                return pcvId;
            }

            public void setPcvId(String pcvId) {
                this.pcvId = pcvId;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getTwoId() {
                return twoId;
            }

            public void setTwoId(String twoId) {
                this.twoId = twoId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getWhetherBuy() {
                return whetherBuy;
            }

            public void setWhetherBuy(String whetherBuy) {
                this.whetherBuy = whetherBuy;
            }

            public String getWhetherFree() {
                return whetherFree;
            }

            public void setWhetherFree(String whetherFree) {
                this.whetherFree = whetherFree;
            }
        }
}
