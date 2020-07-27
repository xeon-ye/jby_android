package com.jkrm.education.bean.result;

import java.util.List;

/**
 * Created by hzw on 2019/7/19.
 */

public class TeachersResultBean {


    /**
     * classList : [{"classId":"607c41fc73154e00829cf6aa0100b36d","courseName":"数学","grade":"27b8b94ae31d5a0bcca1352b20c24e69","course":"09f3d51414a9b14916908c336aa6624e","className":"初一(7)班"}]
     * course : 09f3d51414a9b14916908c336aa6624e
     * grade : 27b8b94ae31d5a0bcca1352b20c24e69
     * groupId :
     * groupName :
     * id : 46132ce55aeb4d17a2d9660f505feb19
     * leaderClassList : [{"grade":"27b8b94ae31d5a0bcca1352b20c24e69","className":"初一(7)班","id":"607c41fc73154e00829cf6aa0100b36d"}]
     * phone :
     * pmsnList : [{"pmsnId":"40fff9ce753f9c3f0b8e38b0d720f408","pmsnName":"班主任"}]
     * schId : 1
     * schName :
     * schPhase : 7f82f6933b76d77757884d74ab9990e0
     * teacherName : 七班班主任
     * userId : dfa6bfefc27b6e7cd33fdad52fe9c499
     * username :
     */

    private String course;
    private String grade;
    private String groupId;
    private String groupName;
    private String id;
    private String phone;
    private String schId;
    private String schName;
    private String schPhase;
    private String teacherName;
    private String userId;
    private String username;
    private List<ClassListBean> classList;
    private List<LeaderClassListBean> leaderClassList;
    private List<PmsnListBean> pmsnList;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getSchPhase() {
        return schPhase;
    }

    public void setSchPhase(String schPhase) {
        this.schPhase = schPhase;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ClassListBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassListBean> classList) {
        this.classList = classList;
    }

    public List<LeaderClassListBean> getLeaderClassList() {
        return leaderClassList;
    }

    public void setLeaderClassList(List<LeaderClassListBean> leaderClassList) {
        this.leaderClassList = leaderClassList;
    }

    public List<PmsnListBean> getPmsnList() {
        return pmsnList;
    }

    public void setPmsnList(List<PmsnListBean> pmsnList) {
        this.pmsnList = pmsnList;
    }

    public static class ClassListBean {
        /**
         * classId : 607c41fc73154e00829cf6aa0100b36d
         * courseName : 数学
         * grade : 27b8b94ae31d5a0bcca1352b20c24e69
         * course : 09f3d51414a9b14916908c336aa6624e
         * className : 初一(7)班
         */

        private String classId;
        private String courseName;
        private String grade;
        private String course;
        private String className;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    public static class LeaderClassListBean {
        /**
         * grade : 27b8b94ae31d5a0bcca1352b20c24e69
         * className : 初一(7)班
         * id : 607c41fc73154e00829cf6aa0100b36d
         */

        private String grade;
        private String className;
        private String id;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class PmsnListBean {
        /**
         * pmsnId : 40fff9ce753f9c3f0b8e38b0d720f408
         * pmsnName : 班主任
         */

        private String pmsnId;
        private String pmsnName;

        public String getPmsnId() {
            return pmsnId;
        }

        public void setPmsnId(String pmsnId) {
            this.pmsnId = pmsnId;
        }

        public String getPmsnName() {
            return pmsnName;
        }

        public void setPmsnName(String pmsnName) {
            this.pmsnName = pmsnName;
        }
    }
}
