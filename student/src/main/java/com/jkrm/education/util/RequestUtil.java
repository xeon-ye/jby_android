package com.jkrm.education.util;

import android.util.Log;

import com.google.gson.Gson;
import com.hzw.baselib.api.FormDataConvertUtil;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMd5Util;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.SecurityUtils;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.ClassesBean;
import com.jkrm.education.bean.ExportQuesionBasketRequestBean;
import com.jkrm.education.bean.GoodsDetai;
import com.jkrm.education.bean.QuestionBasketAddRequestBean;
import com.jkrm.education.bean.QuestionBean;
import com.jkrm.education.bean.QuestionErrorAddRequestBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.user.SubmitAnswerSheetBean;
import com.jkrm.education.bean.user.UserBean;
import com.jkrm.education.constants.MyConstant;

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
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by hzw on 2019/5/6.
 */

public class RequestUtil {

    public static RequestBody getBody(Object object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(object));
    }

    public static RequestBody getBodyText(String object) {
        return RequestBody.create(MediaType.parse("text/plain"), object);
    }

    /**
     * 临时接口 账号验证
     *
     * @return
     */
    public static RequestBody getUserJudge() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", MyApp.getInstance().getAppUser().getNickName());
        return getBody(hashMap);
        //        return FormDataConvertUtil.getRequestBody(hashMap);
    }

    /**
     * 登录请求
     *
     * @return
     */
    public static RequestBody getLoginRequest(String account, String pwd) {
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            account = SecurityUtils.encrypt(account);
            pwd = SecurityUtils.encrypt(pwd + t);
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
        hashMap.put("t", t);
        return getBody(hashMap);
//        return FormDataConvertUtil.getRequestBody(hashMap);
    }


    /**
     * 获取验证码
     *
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
     *
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
     *
     * @param mobile
     * @param password
     * @return
     */
    public static RequestBody updatePwdRequest(String mobile, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mobile);
        try {
            password = SecurityUtils.encrypt(password);
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

    public static RequestBody updateMobileRequest(String mobile, String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mobile);
        hashMap.put("id", id);
        return getBody(hashMap);
    }

    /**
     * 修改昵称
     *
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
     * 修改头像
     *
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
     *
     * @param passwordOld
     * @param passwordOne
     * @param passwordTwo
     * @return
     */
    public static RequestBody updateUserPwd(String passwordOld, String passwordOne, String passwordTwo) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", UserUtil.getUsername());
        try {
            passwordOld = SecurityUtils.encrypt(passwordOld);
            passwordOne = SecurityUtils.encrypt(passwordOne);
            passwordTwo = SecurityUtils.encrypt(passwordTwo);
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
     *
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
     * 学生作业列表请求
     *
     * @return
     */
    public static RequestBody getAnswerSheetRequest(String start_date, String end_date, String course_id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("start_date", start_date);
        hashMap.put("end_date", end_date);
        hashMap.put("course_id", course_id);
        hashMap.put("student_id", MyApp.getInstance().getAppUser().getId());
        return getBody(hashMap);
    }

    /**
     * 添加某题到题篮请求
     *
     * @param bean
     * @return
     */
    public static RequestBody addQuestionBasketRequest(QuestionBasketAddRequestBean bean) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("homeworkId", bean.getHomeworkId());
        hashMap.put("questionId", bean.getQuestionId());
        hashMap.put("questionNum", bean.getQuestionNum());
        hashMap.put("classId", bean.getClassId());
        hashMap.put("courseId", bean.getCourseId());
        hashMap.put("questionTypeId", bean.getQuestionTypeId());
        hashMap.put("studentId", bean.getStudentId());
        hashMap.put("studCode", bean.getStudCode());
        hashMap.put("answer", bean.getAnswer());
        hashMap.put("scanImage", bean.getScanImage());
        return getBody(hashMap);
    }

    /**
     * 添加某题到错题请求
     *
     * @param bean
     * @return
     */
    public static RequestBody addQuestionErrorRequest(ErrorQuestionResultBean bean) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("homeworkId", bean.getHomeworkId());
        hashMap.put("questionId", bean.getQuestionId());
        hashMap.put("questionNum", bean.getQuestionNum());
        hashMap.put("classId", UserUtil.getStudClassId());
        hashMap.put("courseId", bean.getCourseId());
        hashMap.put("questionTypeId", bean.getTypeId());
        hashMap.put("studentId", UserUtil.getStudId());
        hashMap.put("studCode", UserUtil.getStudCode());
        hashMap.put("answer", bean.getAnswer());
        hashMap.put("scanImage", bean.getRawScan());
        hashMap.put("templateId", bean.getTemplateId());
        return getBody(hashMap);
    }


    /**
     * 导出题篮请求
     *
     * @param ids
     * @return
     */
    public static RequestBody exportQuestionBasketRequest(String ids) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("allIds", ids);
//        hashMap.put("ids", ids);
        hashMap.put("answer", "1"); //是否需要答案 1需要 0不需要
        hashMap.put("explain", "1"); //是否需要解析 1需要 0不需要
//        return FormDataConvertUtil.getRequestBody(hashMap);
        return getBody(hashMap);
    }

    /**
     * 导出题篮请求
     *
     * @return
     */
    public static RequestBody exportQuestionBasketRequest(ExportQuesionBasketRequestBean bean) {
        return getBody(bean);
    }

    /**
     * 空的请求
     *
     * @return
     */
    public static RequestBody emptyRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        return getBody(hashMap);
    }

    /**
     * 请求微课
     *
     * @param pcvId
     * @param typeId
     * @return
     */
    public static RequestBody getCourseRequestBody(String pcvId, String typeId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pcvId", pcvId);
        hashMap.put("typeId", typeId);
        return getBody(hashMap);
    }

    /**
     * 请求微课播放列表
     *
     * @param mlessonId
     * @param pcvId
     * @return
     */
    public static RequestBody getCoursePlayListBody(String mlessonId, String pcvId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mlessonId", mlessonId);
        hashMap.put("pcvId", pcvId);
        return getBody(hashMap);
    }

    /**
     * 创建订单
     *
     * @param orderName
     * @param goodsPrice
     * @param orderType      1支付 2 充值
     * @param goodsNum
     * @param goodsDetaiList
     * @return
     */
    public static RequestBody getCreateOrderBody(String orderName, String goodsPrice, String orderType, String goodsNum, List<GoodsDetai> goodsDetaiList,String course_id,String course_name) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", UserUtil.getUserId());
        hashMap.put("orderName", orderName);
        hashMap.put("schoolId", UserUtil.getAppUser().getSchool().getId());
        hashMap.put("roleld", UserUtil.getRoleld());
        hashMap.put("goodsPrice", goodsPrice);
        hashMap.put("client", "ANDROID");
        hashMap.put("orderType", orderType);
        hashMap.put("goodsNum", goodsNum);
        hashMap.put("detaiList", goodsDetaiList);
        hashMap.put("course_id",course_id);
        hashMap.put("course_name",course_name);
        return getBody(hashMap);
    }

    /**
     * c创建微信订单
     *
     * @param orderId   上一接口返回订单id
     * @param total_fee 价钱
     *                  changeType  1充值  2.支付
     * @return
     */
    public static RequestBody getCreateWechatOrderBody(String orderId, String total_fee, String changeType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", orderId);
        hashMap.put("total_fee", total_fee);
        hashMap.put("changeType", changeType);
        return getBody(hashMap);
    }

    /**
     * 钱包支付
     *
     * @param orderId
     * @param changeType
     * @return
     */
    public static RequestBody gerCreatePursePayOrderBody(String orderId, String total_fee, String changeType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", orderId);
        hashMap.put("total_fee", total_fee);
        hashMap.put("changeType", changeType);
        return getBody(hashMap);
    }

    /**
     * 保存视频观看记录
     *
     * @param goodsId
     * @param goodsName
     * @param goodsUrl
     * @param videoId
     * @param watchTime   计时器时长
     * @param videoTime   播放器进度  单位 秒
     * @param totalTime   总时长
     * @param watchStatus 0 未完成  1  完成
     * @return
     */
    public static RequestBody getSaveWatchLogBody(String goodsId, String goodsName, String goodsUrl, String videoId, String watchTime, String videoTime, String totalTime, String watchStatus) {
        HashMap<String, String> hashMap = new HashMap<>();
        String userId = UserUtil.getUserId();
        hashMap.put("userId", UserUtil.getUserId());
        hashMap.put("goodsId", goodsId);
        hashMap.put("goodsName", goodsName);
        hashMap.put("goodsUrl", goodsUrl);
        hashMap.put("videoId", videoId);
        hashMap.put("watchTime", watchTime);
        hashMap.put("videoTime", videoTime);
        hashMap.put("totalTime", totalTime);
        hashMap.put("watchStatus", watchStatus);
        hashMap.put("module", "video");
        hashMap.put("msg", "");
        return getBody(hashMap);
    }

    /**
     * @param goodsId 微课id
     * @return
     */
    public static RequestBody getWatchListBody(String goodsId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("goodsId", goodsId);
        return getBody(hashMap);

    }


    /**
     * 保存练习接口
     *
     * @param studentId
     * @param courseId
     * @param type
     * @param questions
     * @return
     */
    public static RequestBody getSaveReinforceBody(String studentId, String courseId, String type, List<QuestionBean> questions, String useTime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("courseId", courseId);
        hashMap.put("type", type);//1错题  2类题
        hashMap.put("questions", questions);
        hashMap.put("useTime", useTime);
        String s = new Gson().toJson(questions).toString();
        return getBody(hashMap);
    }

    /**
     * 收藏
     *
     * @param studentId
     * @param courseId
     * @param questionId
     * @param type///    '收藏来源：1-错题重练；2-类题加练'
     * @return
     */
    public static RequestBody getCollectBody(String studentId, String courseId, String questionId, String type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("courseId", courseId);
        hashMap.put("questionId", questionId);
        hashMap.put("type", type);
        return getBody(hashMap);
    }


    /**
     * 移除收藏
     *
     * @param studentId
     * @param questionId
     * @return
     */
    public static RequestBody getRemoveCollectBody(String studentId, String questionId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("questionId", questionId);
        return getBody(hashMap);
    }

    /**
     * 自己批阅
     *
     * @param batchId
     * @param rightOrError //0 错误  1  正确
     * @param questionId
     * @return
     */
    public static RequestBody getReadOverBody(String batchId, String rightOrError, String questionId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("batchId", batchId);
        hashMap.put("rightOrError", rightOrError);
        hashMap.put("questionId", questionId);
        return getBody(hashMap);
    }

    /**
     * 答题详情
     *
     * @param studentId
     * @param courseId
     * @return
     */
    public static RequestBody getAnswerRecordBody(String studentId, String courseId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("courseId", courseId);
        return getBody(hashMap);
    }

    /**
     * 错题本按时间分类
     *
     * @param studentId
     * @param courseId
     * @param errorType
     * @param current
     * @param size
     * @return
     */
    public static RequestBody getByTimeBody(String studentId, String courseId, String errorType, String current, String size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("courseId", courseId);
        hashMap.put("errorType", errorType);
        hashMap.put("current", current);
        hashMap.put("size", size);
        return getBody(hashMap);
    }

    /**
     * 错题本按分类
     *
     * @param studentId
     * @param courseId
     * @param tabType
     * @return
     */
    public static RequestBody getByClassifyBody(String studentId, String courseId, String tabType) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", studentId);
        hashMap.put("courseId", courseId);
        hashMap.put("tabType", tabType);
        return getBody(hashMap);
    }

    /**
     * 获取错题详情
     *
     * @param templateId
     * @param tabType
     * @param courseId
     * @param studentId
     * @return
     */
    public static RequestBody getErrorDetailBody(String templateId, String tabType, String courseId, String studentId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("templateId", templateId);
        hashMap.put("tabType", tabType);
        hashMap.put("courseId", courseId);
        hashMap.put("studentId", studentId);
        return getBody(hashMap);
    }

    /**
     * 答题卡 提交
     *
     * @param templateId
     * @param studentId
     * @param answerSheetBean
     * @return
     */
    public static RequestBody getSubmitAnswerBody(String templateId, String studentId, AnswerSheetBean answerSheetBean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<SubmitAnswerSheetBean> objects = new ArrayList<>();
        hashMap.put("templateId", templateId);
        hashMap.put("studentId", studentId);
        for (AnswerSheetBean.QuestionsBean question : answerSheetBean.getQuestions()) {
            for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestion : question.getSubQuestions()) {
                SubmitAnswerSheetBean submitAnswerSheetBean = new SubmitAnswerSheetBean();
                submitAnswerSheetBean.setQuestionId(subQuestion.getId());
                if (!AwDataUtil.isEmpty(subQuestion.getStuAnswer())) {
                    submitAnswerSheetBean.setAnswer(subQuestion.getStuAnswer());

                }
                if (!AwDataUtil.isEmpty(subQuestion.getImageList())) {
                    submitAnswerSheetBean.setAnswer(AwArraysUtil.listToString(subQuestion.getImageList()));
                }
                objects.add(submitAnswerSheetBean);
            }
        }
        hashMap.put("questions", objects);
        return getBody(hashMap);
    }

    /**
     * 获取地址联动
     * @param pcode
     * @return
     */
    public static RequestBody getResions(String pcode) {
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("encrypt", AwMd5Util.md5("jby-xinjiaoyu" + t + pcode));
        hashMap.put("t", t);
        hashMap.put("pcode", pcode);
        return getBody(hashMap);
    }

    /**
     * 获取学校列表
     * @param provId
     * @param areaId
     * @param cityId
     * @return
     */
    public static RequestBody getSchoolList(String provId,String areaId,String cityId){
        String t = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("encrypt", AwMd5Util.md5("jby-xinjiaoyu" + t + provId+areaId+cityId));
        hashMap.put("t", t);
        hashMap.put("provId", provId);
        hashMap.put("areaId", areaId);
        hashMap.put("cityId", cityId);
        return getBody(hashMap);
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
        hashMap.put("typeInfo", 1);
        return  getBody(hashMap);
    }

    /**
     * 补全用户信息
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
        hashMap.put("enrollmentYear",Integer.parseInt(enrollmentYear.replaceAll("年","")));
        hashMap.put("courseId",courseId);
        hashMap.put("typeInfo", 1);
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
    public static  RequestBody bindSchoolClass(String id,String gradeId,String enrollmentYear,String classId,String className,String studentId,String teacherId,ArrayList<ClassesBean> list){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("typeInfo",1);
        hashMap.put("classId",classId);
        hashMap.put("gradeId",gradeId);
        hashMap.put("enrollmentYear",enrollmentYear.replaceAll("年",""));
        hashMap.put("className",className);
        hashMap.put("studentId",studentId);
        hashMap.put("teacherId",teacherId);
        hashMap.put("classes",list);

        return  getBody(hashMap);
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
     * 修改真实姓名
     * @return
     */
    public static RequestBody updateRealNameRequest(String realName,String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("realName", realName);
        hashMap.put("id", id);
        hashMap.put("typeInfo",1);
        return getBody(hashMap);
    }

    /**
     * 获取订单列表
     * @param current
     * @param size
     * @param step
     * @return
     */
    public static RequestBody getOrderListBody(String current,String size,String step){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("current", current);
        hashMap.put("size", size);
        hashMap.put("step",step);
        return getBody(hashMap);
    }


}