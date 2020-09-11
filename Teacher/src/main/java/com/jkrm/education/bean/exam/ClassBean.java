package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/11 16:45
 */

public class ClassBean implements Serializable {

    /**
     * code : 200
     * data : [{"classId":"2c8c64e64c074464a554723a4cf05438","className":"12班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"b28fcf45f47c4f4b8c5dd325c51e5d79","className":"17班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"da25822506494392a2c674898e15a354","className":"18班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"ecf29982628144378db299eab2c38232","className":"5班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"1b4cd00e59f94b2f990bf53e5ac01878","className":"25班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"6311a5392db44443880898c28c86a6f6","className":"9班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"b7b5ae9e8bd44ed990f048717b79ea72","className":"13班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"1dd2d7f4983843228886eaecc30d9d74","className":"21班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"d45c06d2294a44b8a97b9588d021afc2","className":"24班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"3f8cba66e6d5491f9e1a26c617d8bd48","className":"20班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"b7456f14b703435abdf0bd3273abd646","className":"19班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"fa274e8d05a84d358fbf70618ca85308","className":"8班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"ae64d0b7f7cd482fabe16338959dc880","className":"4班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"acaaa08ae93e4deca3c50600737de068","className":"14班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"24f827139ed8494cb56b96b365d898a2","className":"23班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"8f69d00a3b0348f08bc2df4c4a5e5269","className":"16班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"806fdcda96bf4ca69ea55882e907a327","className":"1班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"9e74b6cceaf54cc5a7359ab1047de196","className":"6班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"b0b5dcc5b5b0411c82bdc9a377921351","className":"22班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"c7669cb0c8514b249cd010b1843817ce","className":"15班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"082a528295084c72aabc56ad7b824f60","className":"7班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"classId":"0BBE6B5B837242FA94DDAB792B8D2DB8","className":"1班","course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"}]
     * msg :
     */

    /**
     * classId : 2c8c64e64c074464a554723a4cf05438
     * className : 12班
     * course : eb96a394615f679a562ef6acdf104dbe
     * courseName : 英语
     */

    private String classId;
    private String className;
    private String course;
    private String courseName;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
