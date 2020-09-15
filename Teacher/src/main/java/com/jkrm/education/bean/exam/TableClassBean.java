package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/10 17:06
 * Description: 报表-获取班级列表
 */
public class TableClassBean implements Serializable {


    /**
     * code : 200
     * data : [{"classId":"b0b5dcc5b5b0411c82bdc9a377921351","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"22班"},{"classId":"28d18adf7b8042d0a0a499f541132f4b","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"10班"},{"classId":"9e74b6cceaf54cc5a7359ab1047de196","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"6班"},{"classId":"8f69d00a3b0348f08bc2df4c4a5e5269","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"16班"},{"classId":"24f827139ed8494cb56b96b365d898a2","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"23班"},{"classId":"806fdcda96bf4ca69ea55882e907a327","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"1班"},{"classId":"3f8cba66e6d5491f9e1a26c617d8bd48","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"20班"},{"classId":"fa274e8d05a84d358fbf70618ca85308","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"8班"},{"classId":"b28fcf45f47c4f4b8c5dd325c51e5d79","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"17班"},{"classId":"ae64d0b7f7cd482fabe16338959dc880","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"4班"},{"classId":"6311a5392db44443880898c28c86a6f6","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"9班"},{"classId":"da25822506494392a2c674898e15a354","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"18班"},{"classId":"1dd2d7f4983843228886eaecc30d9d74","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"21班"},{"classId":"b7b5ae9e8bd44ed990f048717b79ea72","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"13班"},{"classId":"ecf29982628144378db299eab2c38232","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"5班"},{"classId":"2c8c64e64c074464a554723a4cf05438","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"12班"},{"classId":"bf0b8650db11434195780f6f09946d71","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"2班"},{"classId":"c9103e562a4048149fabe2779bbb6938","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"11班"},{"classId":"d45c06d2294a44b8a97b9588d021afc2","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"24班"},{"classId":"acaaa08ae93e4deca3c50600737de068","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"14班"},{"classId":"b7456f14b703435abdf0bd3273abd646","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"19班"},{"classId":"082a528295084c72aabc56ad7b824f60","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"7班"},{"classId":"c7669cb0c8514b249cd010b1843817ce","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"15班"},{"classId":"67574d7db09c46088ed05fa5da312eb8","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"3班"},{"classId":"1b4cd00e59f94b2f990bf53e5ac01878","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"25班"},{"classId":"8B2EF0E417B747B588FBFD3C09E9D6E9","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"2班"},{"classId":"0BBE6B5B837242FA94DDAB792B8D2DB8","courseName":"数学","course":"2abad4627d44c059a599dcd55b40869b","className":"1班"}]
     * msg :
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * classId : b0b5dcc5b5b0411c82bdc9a377921351
         * courseName : 数学
         * course : 2abad4627d44c059a599dcd55b40869b
         * className : 22班
         */

        private String classId;
        private String courseName;
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
}
