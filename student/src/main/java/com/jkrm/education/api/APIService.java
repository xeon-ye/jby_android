package com.jkrm.education.api;


import android.support.v7.widget.RecyclerView;

import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.BatchQuestionBean;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.bean.OriginalPagerResultBean;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.RegisterInitUserBean;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.bean.SaveReinforceBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.common.ResponseListBean;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.jkrm.education.bean.result.AccountBalancesBean;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.bean.result.BookExercisesListBean;
import com.jkrm.education.bean.result.ClassHourBean;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.CreateAliPayOrderResultBean;
import com.jkrm.education.bean.result.CreateOrderResultBean;
import com.jkrm.education.bean.result.CreateWechatPayOrderResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.InvestResultBean;
import com.jkrm.education.bean.result.LessonHourBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.PurseResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.ReportDurationsResultBean;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;
import com.jkrm.education.bean.result.ReportQuestionScoreResultBean;
import com.jkrm.education.bean.result.RequestClassesBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.bean.result.TemplateInfoResultBean;
import com.jkrm.education.bean.result.TopicScoreBean;
import com.jkrm.education.bean.result.UserLoginVerifyResultBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.result.WatchResultBean;
import com.jkrm.education.constants.UrlConstant;

import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hzw on 2019/2/17.
 */
public interface APIService {

    /**
     * 分页查询时每页数据量
     */
    public static final int COMMON_PAGE_SIZE = 50;

    /**
     * 登录
     * @return
     */

    @POST(UrlConstant.USER_LOGIN)
    Observable<ResponseBean<LoginResult>> login(@Body RequestBody requestBody);

    /**
     * 根据用户名获取用户信息(返回不全, 暂不用)
     * @param username
     * @return
     */
    @GET(UrlConstant.USER_INFO_BY_USERNAME)
    Observable<ResponseBean<LoginResult>> getUserInfo(@Path("username") String username);

    /**
     * 获取手机验证码
     */
    @POST(UrlConstant.GET_PHONE_CODE)
    Observable<ResponseBean<String>> getPhoneCode(@Body RequestBody requestBody);

    /**
     * 验证手机验证码
     */
    @POST(UrlConstant.VERIFY_PHONE_CODE)
    Observable<ResponseBean<String>> verifyPhoneCode(@Body RequestBody requestBody);

    /**
     * 验证登录
     */
    @POST(UrlConstant.USER_LOGIN_VERIFY)
    Observable<ResponseBean<UserLoginVerifyResultBean>> verifyUserLogin(@Body RequestBody requestBody);

    /**
     * 修改密码
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_PWD)
    Observable<ResponseBean<String>> updateUserPwd(@Body RequestBody requestBody);


    /**
     * 修改密码
     * @return
     */
    @POST(UrlConstant.USER_UPDATE_USER_PWD)
    Observable<ResponseBean<String>> modifyUserPwd(@Body RequestBody requestBody);

    /**
     * 修改手机号
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_MOBILE)
    Observable<ResponseBean<String>> updateUserMobile(@Body RequestBody requestBody);

    /**
     * 修改昵称
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_NICKNAME)
    Observable<ResponseBean<String>> updateUserNickname(@Body RequestBody requestBody);

    /**
     * 修改头像
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_AVATAR)
    Observable<ResponseBean<String>> updateUserAvatar(@Body RequestBody requestBody);

    /**
     * 版本更新
     *
     * @return
     */
    @GET(UrlConstant.VERSION_CHECK)
    Observable<ResponseBean<VersionResultBean>> getVersionInfo(@Query("systemType") String systemType, @Query("orgType") String orgType, @Query("environType") String environType);

    /**
     * 获取用户信息
     * @return
     */

    @GET(UrlConstant.USER_JUDGE)
    Observable<ResponseBean<String>> getUserJudge(@Query("username") String username);

    /**
     * 获取学生作业列表
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT)
    Observable<ResponseListBean<AnswerSheetDataResultBean, RowsHomeworkBean>> answer_sheets(@Path("student_id") String student_id,
                                                                                            @Query("start_date") String start_date,
                                                                                            @Query("end_date") String end_date,
                                                                                            @Query("class_id") String class_id,
                                                                                            @Query("course_id") String course_id,
                                                                                            @Query("current") int page_,
                                                                                            @Query("size") int limit_);

    /**
     * 获取学生作业列表(所有的 仅有名称和id返回, 非分页)
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT_ALL)
    Observable<ResponseBean<List<AnswerSheetAllDataResultBean>>> answer_sheets_all(@Path("student_id") String student_id, @Query("courseId") String course_id);

    /**
     * 获取学生错题本学科列表
     * @param studentId
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_LIST_SUBJECT)
    Observable<ResponseBean<List<SubjectDataResultBean>>> subject_all(@Path("studentId") String studentId);


    /**
     * 获取学生答题卡记录---作业详情
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_DETAIL_STUDENT)
    Observable<ResponseBean<List<AnswerSheetDataDetailResultBean>>> answerSheetsQuestion(@Path("answer_sheet_id") String answer_sheet_id);

    /**
     * 获取学生答题卡记录---成绩分布
     * @return
     */
    @GET(UrlConstant.REPORT_DURATIONS)
    Observable<ResponseBean<List<ReportDurationsResultBean>>> report_durations(@Path("homeworkId") String homeworkId);

    /**
     * 获取学生答题卡记录---得分
     * @return
     */
    @GET(UrlConstant.REPORT_QUESTIONSCORE)
    Observable<ResponseBean<List<ReportQuestionScoreResultBean>>> report_question(@Path("homeworkId") String homeworkId, @Path("studentId") String studentId);

    /**
     * 获取学生答题卡记录---得分率
     * @return
     */
    @GET(UrlConstant.REPORT_QUESTIONSCORERATE)
    Observable<ResponseBean<List<ReportQuestionScoreRateResultBean>>> report_questionRate(@Path("homeworkId") String homeworkId, @Path("studentId") String studentId);

    /**
     * 根据作业id获取班级，原题总分，学生得分
     * @return
     */
    @GET(UrlConstant.CLASS_SCORE_MAXSCORE)
    Observable<ResponseBean<MaxScoreResultBean>> getClassScoreMaxScore(@Path("answer_sheet_id") String answer_sheet_id);


    /**
     * 获取某题(原题等)
     * @return
     */
    @GET(UrlConstant.QUESTION_GET)
    Observable<ResponseBean<QuestionResultBean>> getQuestion(@Path("questionId") String questionId);

    /**
     * 获取题目拓展
     * @return
     */
    @GET(UrlConstant.QUESTION_SIMILAR)
    Observable<ResponseBean<List<SimilarResultBean>>> getSimilar(@Path("questionId") String questionId);

    /**
     * 根据题目ID获取对点微课和名师讲题
     * @return
     */
    @GET(UrlConstant.QUESTION_VIDEOS)
    Observable<ResponseBean<VideoResultBean>> getVideos(@Path("questionId") String questionId);


    /**
     * 根据学生id获取教师用户列表
     * @return
     */
    @GET(UrlConstant.USER_TEACHER_LIST)
    Observable<ResponseBean<List<TeachersResultBean>>> getMyTeacherList(@Path("stuId") String student_id);

    /**
     * 获取错题本
     * @return
     */
    @GET(UrlConstant.MISTAKE_LIST)
    Observable<ResponseBean<List<ErrorQuestionResultBean>>> getMistakeList(@Path("homeworkId") String homeworkId, @Path("studentId") String studentId);

    /**
     * 获取错题本(改版)
     * @param student_id
     * @param current
     * @param size
     * @param start_date
     * @param end_date
     * @param homework_id
     * @param course_id 学科id
     * @param quest_type 题型id
     * @param order_type 排序方式 1时间2个人得分率3班级得分率
     * @return
     */
    @GET(UrlConstant.MISTAKE_LIST_NEW)
    Observable<ResponseListBean<AnswerSheetDataResultBean, ErrorQuestionResultBean>> getMistakeListNew(@Path("student_id") String student_id, @Query("current") String current, @Query("size") String size,
                                                                           @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("homework_id") String homework_id,
                                                                           @Query("course_id") String course_id, @Query("quest_type") String quest_type, @Query("order_type") String order_type);
    /**
     * 获取练习记录(废弃)
     * @param student_id
     * @param current
     * @param size
     * @param start_date
     * @param end_date
     * @param homework_id
     * @param course_id 学科id
     * @param quest_type 题型id
     * @param order_type 排序方式 1时间2个人得分率3班级得分率
     * @return
     */
    @GET(UrlConstant.MISTAKE_LIST_NEW)
    Observable<ResponseListBean<AnswerSheetDataResultBean, PracticeResultBean>> getPracticeList(@Path("student_id") String student_id, @Query("current") String current, @Query("size") String size,
                                                                                                  @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("homework_id") String homework_id,
                                                                                                  @Query("course_id") String course_id, @Query("quest_type") String quest_type, @Query("order_type") String order_type);

    /**
     * 获取练习记录
     * @return
     */
    @GET(UrlConstant.PRACTICE_EXERCISES)
    Observable<ResponseBean<PracticeDataResultBean>> getPracticeDataList(@Path("studendId") String studendId,
                                                                         @Query("startDate") String startDate, @Query("endDate") String endDate,
                                                                         @Query("courseId") String courseId, @Query("questionTypeId") String questionTypeId);

    /**
     * 获取练习记录柱图数据
     * @return
     */
    @GET(UrlConstant.PRACTICE_TABLE)
    Observable<ResponseBean<List<PracticeTableResult>>> getPracticeDataTable(@Path("studendId") String studendId,
                                                                                 @Query("startDate") String startDate, @Query("endDate") String endDate);

    /**
     * 查询某次作业下的所有对点微课
     */
    @GET(UrlConstant.VIDEO_POINT_LIST)
    Observable<ResponseBean<List<VideoPointResultBean>>> getVideoPointList(@Path("homework_id") String homework_id);

    /**
     * 查询某次作业下的所有对点微课(新)
     */
    @GET(UrlConstant.VIDEO_POINT_LIST_NEW)
    Observable<ResponseBean<List<VideoPointResultBean>>> getVideoPointListNew(@Path("homeworkId") String homeworkId);

    /**
     * 查询某次作业下的所有对点微课(根据模板id, 扫描纸质作业的二维码使用)
     */
    @GET(UrlConstant.VIDEO_POINT_LIST_BY_TEMPLATE)
    Observable<ResponseBean<List<VideoPointResultBean>>> getVideoPointListByTemplate(@Path("template_id") String template_id);

    /**
     * 从题篮移出某题
     * @param questionId
     * @param studentId
     * @return
     */
    @DELETE(UrlConstant.QUESTION_BASKET_DEL)
    Observable<ResponseBean<String>> delSomeOneQuestionBasket(@Path("questionId") String questionId, @Path("studentId") String studentId);

    /**
     * 清空题篮
     * @param studentId
     * @return
     */
    @DELETE(UrlConstant.QUESTION_BASKET_DEL_ALL)
    Observable<ResponseBean<String>> delAllQuestionBasket(@Path("studentId") String studentId);

    /**
     * 添加某题到题篮
     */
    @POST(UrlConstant.QUESTION_BASKET_ADD)
    Observable<ResponseBean<String>> addQuestionBasket(@Body RequestBody requestBody);

    /**
     * 导出题篮
     */
    @POST(UrlConstant.QUESTION_BASKET_EXPORT)
    Observable<ResponseBean<String>> exportQuestionBasket(@Body RequestBody requestBody);

    /**
     * 上传文件到OSS
     *
     * @return
     */
    @Multipart
    @POST(UrlConstant.UPLOAD_FILE)
    Observable<ResponseBean<OssResultBean>> uploadOss(@Part("module") RequestBody module, @Part MultipartBody.Part file);

    /**
     * 删除错题
     * @param studentId
     * @return
     */
    @DELETE(UrlConstant.ERROR_QUESTION_DELETE)
    Observable<ResponseBean<String>> delErrorQuestion(@Path("questionId") String questionId, @Path("studentId") String studentId);

    /**
     * 获取错题统计列表
     */
    @GET(UrlConstant.ERROR_QUESTION_STATISTICS_LIST)
    Observable<ResponseBean<List<ErrorQuestionResultBean>>> getErrorQuestionStatisticsList(@Path("templateId") String templateId, @Query("studentId") String studentId);

    /**
     * 添加错题
     */
    @POST(UrlConstant.ERROR_QUESTION_SAVE)
    Observable<ResponseBean<String>> addErrorQuestion(@Body RequestBody requestBody);

    /**
     * 获取模板信息
     */
    @GET(UrlConstant.TEMPLATE_INFO)
    Observable<ResponseBean<TemplateInfoResultBean>> templateInfo(@Path("templateId") String templateId);

    /**
     *获取图书习题筛选内容
     */
    @GET(UrlConstant.GET_BOOK_EXERCISES_ALL_LIST)
    Observable<ResponseBean<BookExercisesBean>> getBookExercisesList();

    /**
     * 获取课时二级列表
     */
    @GET(UrlConstant.GET_CLASS_HOUR_LIST)
    Observable<ResponseBean<ClassHourBean>>  getClassHour(@Path("class_hour") String class_hour);

    /**
     * 获取课时图书主列表
     */
    @GET(UrlConstant.GET_BOOK_MAIN_LIST)
    Observable<ResponseBean<List<BookExercisesListBean>>> getBookMainList(@Path("class_hour_key") String class_hour_key, @Query("studId") String studid);

    /**
     * 获取题号和得分
     */
    @GET(UrlConstant.GET_TOPIC_SCORE)
    Observable<ResponseBean<List<TopicScoreBean>>> getTopicScore(@Path("class_hour_key") String class_hour_key);

    /**
     * 获取课时id 和微课视频 key
     */
    @GET(UrlConstant.GET_LESSON_HOUR)
    Observable<ResponseBean<LessonHourBean>> getLessonHour(@Path("class_hour_key") String class_hour_key );

    /**
     * 获取微课类型
     */
    @POST(UrlConstant.GET_COURSE_TYPE)
    Observable<ResponseBean<List<CourseTypeBean>>> getCourseType(@Body RequestBody requestBody);

    /**
     * 获取微课属性
     */
    @GET(UrlConstant.GET_COURSE_ATTR)
    Observable<ResponseBean<CourseAttrBean>> getCourseAttr();

    /**
     * 获取微课套餐列表
     */
    @POST(UrlConstant.GET_COURSE_LIST)
    Observable<ResponseBean<List<MicroLessonResultBean>>> getCourseList(@Body RequestBody requestBody );

    /**
     * 微课播放列表
     */
    @POST(UrlConstant.GET_COURSE_PLAY_LIST)
    Observable<ResponseBean<List<CoursePlayResultBean>>> getCoursePlayList(@Body RequestBody requestBody);

    /**
     * 创建订单
     */
    @POST(UrlConstant.CREATE_ORDER)
    Observable<ResponseBean<CreateOrderResultBean>> createOrder(@Body RequestBody requestBody);

    /**
     * 创建微信订单
     */
    @POST(UrlConstant.WECHAT_PAY)
    Observable<ResponseBean<CreateWechatPayOrderResultBean>> createWechatPayOrder(@Body RequestBody requestBody);

    /**
     * 创建支付宝订单
     */
    @GET(UrlConstant.ALI_PAY)
    Observable<CreateAliPayOrderResultBean> createAlipayOrder(@Query("orderNo") String orderNo,@Query("amount") String amount);

    /**
     * 获取账户教育币信息
     */
    @GET(UrlConstant.GET_ACCOUNT_BALANCES)
    Observable<AccountBalancesBean> getAccountBalances(@Path("user_id") String user_id);

    /**
     * 钱包支付
     */
    @POST(UrlConstant.PURSE_PAY)
    Observable<ResponseBean<PurseResultBean>>  pursePay(@Body RequestBody requestBody);

    /**
     * 获取教育币
     */
    @GET(UrlConstant.GET_INVEST_LIST)
    Observable<ResponseBean<List<InvestResultBean>>> getInvestList(@Query("clientType")String clientType);

    /**
     * 获取观看记录
     */
    @POST(UrlConstant.GET_WATCH_LIST)
    Observable<ResponseBean<WatchResultBean>> getWatchList(@Body RequestBody requestBody);

    /**
     * 保存观看记录
     */
    @POST(UrlConstant.SAVE_WATCH_LOG)
    Observable<WatchLogBean> saveWatchLog(@Body RequestBody requestBody);

    /**
     * 查看学生原卷
     * @return
     */
    @GET(UrlConstant.STUDENT_QUESTION_ANSWER_ORIGINAL)
    Observable<ResponseBean<OriginalPagerResultBean>> getStudentOriginalQuestionAnswer(@Path("homework_id") String homework_id, @Path("student_id") String student_id);

    /**
     * 类题加练-学生练习保存接口
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.REINFORCE_SAVE)
    Observable<ResponseBean<SaveReinforceBean>> saveReinforce(@Body RequestBody requestBody);

    /**
     * 答题记录
     * @param batchid
     * @return
     */
    @GET(UrlConstant.GET_BATCH)
    Observable<ResponseBean<List<BatchBean>>> getBatch(@Path("batchid") String batchid);

    /**
     * 答题情况
     */
    @GET(UrlConstant.GET_BATCH_QUESTION)
    Observable<ResponseBean<BatchQuestionBean>>  getBatchQuestion(@Path("batchid") String batchid);


    /**
     * 收藏
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.COLLECT_QUESTON)
    Observable<WatchLogBean>  collectQuestion(@Body RequestBody requestBody);

    /**
     * 取消收藏
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.REMOVE_COLLECT_QUESTON)
    Observable<WatchLogBean>  removeCollectQuestion(@Body RequestBody requestBody);

    /**
     * 批阅
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.READOVER_QUESTON)
    Observable<WatchLogBean>  readOverQuestion(@Body RequestBody requestBody);

    /**
     * 类题加练科目
     * @param studId
     * @return
     */
    @GET(UrlConstant.REINFORCE_COURSE)
    Observable<ResponseBean<List<ReinfoRoceCouseBean>>>  getReinforceCourseList(@Path("studId" )String studId);

    @POST(UrlConstant.REINFORCE_LIST)
    Observable<ResponseBean<ReinforBean>> getReinforceList(@Body RequestBody requestBody);

    /**
     * 新错题本
     * @param studentId
     * @return
     */
    @GET(UrlConstant.GET_ERROR_QUESTION_LIST)
    Observable<ResponseBean<List<ErrorQuestionBean>>>   getErrorQuestionList(@Path("studentId" ) String studentId);

    /***
     * 按分类，列表接口
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.GET_BY_CLASSIFY)
    Observable<ResponseBean<List<ErrorQuestionClassifyBean>>> getByClassify(@Body RequestBody requestBody);





    /***
     * 按时间，列表接口
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.GET_BY_TIME)
    Observable<ResponseBean<List<ErrorQuestionTimeBean>>> getByTime(@Body RequestBody requestBody);



    /***
     * 按时间，列表接口 分页
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.GET_BY_TIME_PAGED)
    Observable<ErrorQuestionTimePagedBean> getByTimePaged(@Body RequestBody requestBody);

    /**
     * 获取错题列表
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.GET_ERROR_DETAIL)
    Observable<ResponseBean<List<ErrorQuestionDetailBean>>> getErrorDetail(@Body RequestBody requestBody);

    /**
     * 答题卡
     * @param templateId
     * @param studentId
     * @return
     */
    @GET(UrlConstant.GET_ANSWER_SHEET)
    Observable<ResponseBean<AnswerSheetBean>>  getAnswerSheet(@Query("templateId")String templateId,@Query("studentId")String studentId);

    /**
     * 提交答题卡
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.SUBMIT_ANSWER_SHEET)
    Observable<WatchLogBean>  submitAnswerSheet(@Body RequestBody requestBody);

    /**
     *  需讲解
     * @param value
     * @param templateId
     * @param studentId
     * @param quesitonId
     * @return
     */
    @POST(UrlConstant.EXPLAIN_QUESTION)
    Observable<WatchLogBean>  explainQuestion(@Path("value" )String value,@Query("templateId")String templateId,@Query("studentId")String studentId,@Query("questionId")String quesitonId);


    /**
     * 添加错题本
     * @param templateId
     * @param studentId
     * @param quesitonId
     * @return
     */
    @POST(UrlConstant.ADD_MISTAKE)
    Observable<ResponseBean<String>> addMistake(@Query("templateId")String templateId,@Query("studentId")String studentId,@Query("questionId")String quesitonId);

    /**
     * 选择地址
     * @return
     */
    @GET(UrlConstant.GET_REGIONS)
    Observable<ResponseBean<List<AddressBean>>>  getResions(@Query("encrypt") String encrypt, @Query("t") String t, @Query("pcode") String pcode);

    /**
     * 学校列表
     * @return
     */
    @GET(UrlConstant.GET_SCHOOL_LIST)
    Observable<SchoolBean>  getSchoolList(@Query("encrypt") String encrypt, @Query("t") String t, @Query("provId") String provId, @Query("cityId")String cityId, @Query("areaId") String areaId);

    /**
     * 用户注册
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.USER_REGISTER)
    Observable<ResponseBean<RegisterBean>> userRegister(@Body RequestBody requestBody);

    /**
     * 补全用户信息
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.COMPLETION_INFO)
    Observable<ResponseBean<LoginResult>> registerInitUser(@Body RequestBody requestBody);

    /**
     * 绑定班级
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.BIND_SCHOOL_CLASS)
    Observable<ResponseBean<String>>  bindSchoolClass(@Body RequestBody requestBody);


    /**
     * 获取学段以及科目
     * @return
     */
    @GET(UrlConstant.GET_PERIOD_COURSE)
    Observable<ResponseBean<PeriodCourseBean>> getPeriodCourse();

    /**
     * 获取近三年班级
     * @param schoolId
     * @return
     */
    @GET(UrlConstant.GET_CLASSES)
    Observable<ResponseBean<Map<String, List<ClassessBean.Bean>>>> getClasses(@Path("schoolId") String schoolId,@Path("userId")String userId);

    /**
     * 验证码登录
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.VERCODE_LOGIN)
    Observable<ResponseBean<LoginResult>> vercodeLogin(@Body RequestBody requestBody);

    /**
     * 获取学生绑定班级
     * @param studId
     * @return
     */
    @GET(UrlConstant.GET_CLASSES_BY_ID)
    Observable<ResponseBean<List<RequestClassesBean>>> getClassesById(@Path("studId")String studId);

    /**
     * 修改真实姓名
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_REALNAME)
    Observable<ResponseBean<String>> updateUserRealName(@Body RequestBody requestBody);


}
