package com.jkrm.education.util;

import com.google.gson.Gson;
import com.hzw.baselib.api.FormDataConvertUtil;
import com.hzw.baselib.util.AwMd5Util;
import com.hzw.baselib.util.SecurityUtils;
import com.jkrm.education.bean.ClassesBean;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 请求体工具类
 * Created by hzw on 2019/5/6.
 */

public class RequestUtil {

    public static RequestBody getBody(Object object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(object));
    }

    /**
     * 版本更新
     * systemType	系统类型（1:安卓，2：IOS）
     * orgType 	组织类型（1：教师，2：学生）
     * environType 	环境（1：测试，2：生产）
     * @return
     */
    public static RequestBody getVersionInfoRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("systemType", "1");
        hashMap.put("orgType", "1");
        hashMap.put("environType", "1");
        return FormDataConvertUtil.getRequestBody(hashMap);
    }
    /**
     * 登录请求
     * @return
     */
    public static RequestBody getLoginRequest(String account, String pwd) {
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            account= SecurityUtils.encrypt(account);
            pwd=SecurityUtils.encrypt(pwd+t);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        hashMap.put("username", account);
        hashMap.put("password", pwd);
        hashMap.put("remember_me", "true");
        hashMap.put("t",t);
        return getBody(hashMap);
//        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public static RequestBody getPhoneCodeRequest(String phone) {
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("t", t);
        hashMap.put("mobile", phone);
        hashMap.put("encrypt", AwMd5Util.md5("jby-xinjiaoyu" + t + phone));
        hashMap.put("codeLen", "6");
        return getBody(hashMap);
    }

    /**
     * 验证手机接收到的验证码
     * @param phone
     * @return
     */
    public static RequestBody verifyPhoneCodeRequest(String phone, String code) {
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("t", t);
        hashMap.put("mobile", phone);
        hashMap.put("encrypt", AwMd5Util.md5("jby-xinjiaoyu" + t + phone + code));
        hashMap.put("code", code);
        return getBody(hashMap);
    }

    /**
     * 修改密码
     * @param mobile
     * @param password
     * @return
     */
    public static RequestBody updatePwdRequest(String mobile, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mobile);
        try {
            password=SecurityUtils.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        hashMap.put("password", password);
        return getBody(hashMap);
    }

    /**
     * 修改手机号
     * @param mobile
     * @param id
     * @return
     */
    public static RequestBody updateMobileRequest(String mobile, String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mobile);
        hashMap.put("id", id);
        return getBody(hashMap);
    }

    /**
     * 修改昵称
     * @param nickName
     * @param id
     * @return
     */
    public static RequestBody updateNicknameRequest(String nickName, String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("nickName", nickName);
        hashMap.put("id", id);
        return getBody(hashMap);
    }

    /**
     * 修改真实姓名
     * @return
     */
    public static RequestBody updateRealNameRequest(String realName,String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("realName", realName);
        hashMap.put("id", id);
        hashMap.put("typeInfo",0);
        return getBody(hashMap);
    }

  /**
     * 修改头像
     * @param avatar
     * @param id
     * @return
     */
    public static RequestBody updateAvatarRequest(String avatar, String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("avatar", avatar);
        hashMap.put("id", id);
        return getBody(hashMap);
    }

    /**
     * 修改密码
     * @param passwordOld
     * @param passwordOne
     * @param passwordTwo
     * @return
     */
    public static RequestBody updateUserPwd(String passwordOld, String passwordOne, String passwordTwo) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", UserUtil.getUsername());
        try {
            passwordOld=SecurityUtils.encrypt(passwordOld);
            passwordOne=SecurityUtils.encrypt(passwordOne);
            passwordTwo=SecurityUtils.encrypt(passwordTwo);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        hashMap.put("passwordOld", passwordOld);
        hashMap.put("passwordOne", passwordOne);
        hashMap.put("passwordTwo", passwordTwo);
        return getBody(hashMap);
    }

    /**
     * 验证登录
     * @param username
     * @param password
     * @return
     */
    public static RequestBody verifyLoginRequest(String username, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", username);
        hashMap.put("password", password);
        return getBody(hashMap);
    }

    /**
     * 添加典型请求
     * @param homeworkId
     * @param questionId
     * @param classId
     * @param gradeId
     * @param answer
     * @param scanImage
     * @param studCode
     * @return
     */
    public static RequestBody getAddSpecialRequest(String homeworkId, String questionId, String classId, String gradeId, String answer, String scanImage, String studCode) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("homeworkId", homeworkId);
        hashMap.put("questionId", questionId);
        hashMap.put("classId", classId);
        hashMap.put("gradeId", gradeId);
        hashMap.put("answer", answer);
        hashMap.put("studCode", studCode);
        hashMap.put("scanImage", scanImage);
        return getBody(hashMap);
    }

    /**
     * 批阅主观题请求
     * @param gradedScan
     * @param score
     * @param status
     * @return
     */
    public static RequestBody getMarkQuestionRequest(String gradedScan, String score, String status) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("gradedScan", gradedScan);
        hashMap.put("score", score);
        hashMap.put("status", status);
        hashMap.put("answer", "");
        return getBody(hashMap);
    }



    /**
     * 批阅选择题请求
     * @param id
     * @param answer
     * @return
     */
    public static RequestBody getUpdateStudentQuestionAnswerRequest(String id, String answer) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("answer", answer);
        return getBody(hashMap);
    }

    /**
     * 统计 --- 作业 --- 提交报表
     * @return
     */
    public static RequestBody getStatisticsHomeworkSubmitTable(String classId,int page) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("classId", classId);
        hashMap.put("current", page+"");
        hashMap.put("size", "10");
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 统计 --- 作业 --- 提交率
     * @return
     */
    public static RequestBody getStatisticsHomeworkRatio(String stime, String etime, String type, String studids, String ids) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("stime", stime);
        hashMap.put("etime", etime);
        hashMap.put("type", type);
        hashMap.put("studids", studids);
        hashMap.put("ids", ids);
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 统计 --- 作业 --- 提交率
     * @return
     */
    public static RequestBody getStatisticsHomeworkErrorQuestionKnowledge(String stime, String etime, String studids, String ids) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("stime", stime);
        hashMap.put("etime", etime);
        hashMap.put("studids", studids);
        hashMap.put("ids", ids);
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 统计 --- 某个教师作业列表(分页)
     * @return
     */
    public static RequestBody getStatisticsScoreHomeworkList(int current, int size) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("current", String.valueOf(current));
        hashMap.put("size", String.valueOf(size));
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 统计 --- 作业统计名次段
     * @return
     */
    public static RequestBody getStatisticsScorePositionRank(String params) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("params", params);
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 统计 --- 作业统计分数段
     * @return
     */
    public static RequestBody getStatisticsScorePositionScore(String params) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("params", params);
        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 取消挂接
     * @param answerSheetId
     * @return
     */
    public static RequestBody unConnectBody(String answerSheetId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("answerSheetId", answerSheetId);
        return  getBody(hashMap);
    }

    /**
     * 注册
     * @param phone
     * @param code
     * @return  typeingfo 0 教师  1 学生
     */
    public static RequestBody getRegisterBody(String phone,String code,String psw,String realName){
        long t = System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("code", code);
        hashMap.put("encrypt", AwMd5Util.md5("jby-xinjiaoyu" + t + phone+code));
        hashMap.put("t", t);
        hashMap.put("password",psw);
        hashMap.put("realName",realName);
        hashMap.put("typeInfo", 0);
        return  getBody(hashMap);
    }

    /**
     *
     * @param id
     * @param phone
     * @param schoolId
     * @param schoolName
     * @param schPhase
     * @param enrollmentYear
     * @param provId
     * @param provName
     * @param cityId
     * @param cityName
     * @param areaId
     * @param areaName
     * @param courseId
     * @return
     */
    public static RequestBody getRegisterInitUserBody(String id,String phone,String schoolId,String schoolName,String schPhase,String enrollmentYear,String provId,String provName,String cityId,String cityName,String areaId,String areaName,String courseId){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("phone",phone);
        hashMap.put("schPhase",schPhase);
        hashMap.put("schoolId",schoolId);
        hashMap.put("schoolName",schoolName);
        hashMap.put("provId",provId);
        hashMap.put("provName",provName);
        hashMap.put("cityId",cityId);
        hashMap.put("cityName",cityName);
        hashMap.put("areaId",areaId);
        hashMap.put("areaName",areaName);
        hashMap.put("enrollmentYear","");
        hashMap.put("courseId",courseId);
        hashMap.put("typeInfo", 0);
        return getBody(hashMap);
    }
    /**
     * 绑定班级
     * @param id  上一步注册返回的接口
     * @param classId
     * @param className
     * @param studentId
     * @param teacherId
     * @return
     */
    public static  RequestBody bindSchoolClass(String id, String gradeId, String enrollmentYear, String classId, String className, String studentId, String teacherId, ArrayList<ClassesBean> list) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("typeInfo", 0);
        hashMap.put("gradeId",gradeId);
        hashMap.put("enrollmentYear",enrollmentYear.replaceAll("年",""));
        hashMap.put("classId", classId);
        hashMap.put("className", className);
        hashMap.put("studentId", studentId);
        hashMap.put("teacherId", teacherId);
        hashMap.put("classes",list);
        return getBody(hashMap);
    }

    /**
     * 验证码登录
     * @param phone
     * @param code
     * @return
     */
    public static RequestBody vercodeLogin(String phone,String code ){
        long t = System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone",phone);
        hashMap.put("code",code);
        hashMap.put("t",t);
        hashMap.put("encrypt",AwMd5Util.md5("jby-xinjiaoyu"+t+phone+code));
        return getBody(hashMap);
    }

    /**
     * 取消绑定班级
     * @param teacherId
     * @param classId
     * @return
     */
    public static RequestBody unbindSchoolClass(String teacherId,String classId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("teacherId",teacherId);
        hashMap.put("classId",classId);
        return getBody(hashMap);
    }

    /**
     * 移除提篮
     * @param teacherId
     * @param questionIds
     * @return
     */
    public static RequestBody deleteErrorBasket(String teacherId,String questionIds){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("teacherId",teacherId);
        hashMap.put("questionIds",questionIds);
        return getBody(hashMap);
    }

    /**
     * 获取选择题分析
     * @param classIds
     * @param questionId
     * @return
     */
    public static RequestBody getErrorStatisticsBody(String classIds,String templateIds,String questionId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("templateIds",templateIds);
        hashMap.put("classIds",classIds);
        hashMap.put("questionid",questionId);
        return getBody(hashMap);
    }

    /**
     * 考试批阅
     * @return
     */
    public static RequestBody getExamMarkRequest(String id, String answerId, String score,String gradedScan,String optStatus,String readWay ,String questinId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("answerId", answerId);
        hashMap.put("score", score);
        hashMap.put("gradedScan", gradedScan);
        hashMap.put("optStatus", optStatus);
        hashMap.put("status", "");
        hashMap.put("answer", "");
        hashMap.put("readWay", readWay);
        hashMap.put("questinId",questinId);
        return getBody(hashMap);
    }


    /**
     * 获取成绩分析报表
     * @param current
     * @param gradeId
     * @param roleId
     * @param size
     * @param classId
     * @param teacherId
     * @return
     */
    public static RequestBody getAnalysisRequestBody(String current,String examName,String gradeId,String roleId,String size,String classId,String teacherId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("current",current);
        hashMap.put("examCategory","");
        hashMap.put("gradeId",gradeId);
        hashMap.put("roleId",roleId);
        hashMap.put("size",size);
        hashMap.put("classId",classId);
        hashMap.put("teacherId",teacherId);
        hashMap.put("beginTime","");
        hashMap.put("examName",examName);
        return getBody(hashMap);
    }

    /**
     * 获取年级列表
     * @param current
     * @param schId
     * @param size
     * @return
     */
    public static RequestBody getGradeList(String current,String schId,String size){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("current",current);
        hashMap.put("schId",schId);
        hashMap.put("size",size);
        return getBody(hashMap);
    }

    /**
     * 综合成绩表
     *
     * @param classId
     * @param examId
     * @return
     */
    public static RequestBody MultipleAchievementBody(String classId, String examId, String keywords) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("classId", classId);
        hashMap.put("examId", examId);
        hashMap.put("keywords", keywords);
        return getBody(hashMap);
    }
    /**
     * 班级成绩对比表
     *
     * @param classId
     * @param examId
     * @return
     */
    public static RequestBody ClassAchievementBody(String classId, String examId, String courseId,String params) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("classId", classId);
        hashMap.put("examId", examId);
        hashMap.put("courseId", courseId);
        hashMap.put("params", params);
        return getBody(hashMap);
    }
    /**
     * 小题得分表
     *
     * @param classId
     * @param examId
     * @return
     */
    public static RequestBody ScoreAchievementBody(String classId, String examId, String courseId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("classId", classId);
        hashMap.put("examId", examId);
        hashMap.put("courseId", courseId);
        return getBody(hashMap);
    }
    /**
     * 成绩分段表
     *
     * @param classId
     * @param examId
     * @return
     */
    public static RequestBody SectionAchievementBody(String classId, String examId, String courseId,String current,String size,String params) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("classId", classId);
        hashMap.put("examId", examId);
        hashMap.put("courseId", courseId);
        hashMap.put("current", current);
        hashMap.put("size", size);
        hashMap.put("params", params);
        return getBody(hashMap);
    }

    /**
     * 获取报表
     * @param examId
     * @param studId
     * @return
     */
    public static RequestBody getReportForm( String examId, String studId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("examId", examId);
        hashMap.put("studId", studId);
        return getBody(hashMap);
    }

}
