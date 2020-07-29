package com.jkrm.education.api;


import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.AwResponseListBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.jkrm.education.bean.ClassesBean;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.ErrorChoiceStatisticsBean;
import com.jkrm.education.bean.result.ErrorSubStatisticsBean;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.HomeworkStudentAnswerWithSingleQuestionResultBean;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.NormalBean;
import com.jkrm.education.bean.result.OriginalPagerResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.QuestionSpecialResultBean;
import com.jkrm.education.bean.result.ReportDurationsResultBean;
import com.jkrm.education.bean.result.ResolveTeacherProgressResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionKnowledgeResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.StatisticsScoreAverageResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionRankResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionScoreResultBean;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.result.TeacherClassCouresResultBean;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.bean.result.UserLoginVerifyResultBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.result.error.ErrorClassesBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.result.error.ErrorHomeWork;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.bean.result.login.UserInfoResult;
import com.jkrm.education.constants.UrlConstant;

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
     *
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
     * 重置密码
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
    Observable<ResponseBean<VersionResultBean>> getVersionInfo( @Query("systemType") String systemType,  @Query("orgType") String orgType,  @Query("environType") String environType);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET(UrlConstant.USER_ME_INFO)
    Observable<ResponseBean<UserInfoResult>> getUserInfo();


    /**
     * 获取教师作业列表筛选条件列表
     *
     * @return
     */
    @GET(UrlConstant.TEACHER_CLASS_COURSES_LIST)
    Observable<ResponseBean<List<TeacherClassCouresResultBean>>> getTeacherClassAndCourseList(@Path("teacher_id") String teacher_id);

    /**
     * 获取教师作业列表
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_LIST_TEACHER)
    Observable<AwResponseListBean<AnswerSheetDataResultBean, RowsHomeworkBean>> answer_sheets(@Path("teacher_id") String teacher_id,
                                                                                              @Query("start_date") String start_date,
                                                                                              @Query("end_date") String end_date,
                                                                                              @Query("class_ids") String class_ids,
                                                                                              @Query("course") String course,
                                                                                              @Query("current") int current,
                                                                                              @Query("size") int size);

    /**
     * 获取教师作业详情
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_DETAIL_TEACHER)
    Observable<ResponseBean<HomeworkDetailResultBean>> homeworkDetail(@Path("homeworkId") String homeworkId, @Path("classId") String classId);

    /**
     * 获取教师作业扫描进度(推送信息后查询用)
     * @return
     */
    @GET(UrlConstant.TEACHER_RESOLVE_PROGRESS)
    Observable<ResponseBean<List<ResolveTeacherProgressResultBean>>> getTeacherResolveProgress(@Path("teacherId") String teacherId);

    /**
     * 获取某作业某题学生作答(答题详情)
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_STUDENT_SCORE_WITH_QUESTION_SINGLE)
    Observable<ResponseBean<List<HomeworkStudentAnswerWithSingleQuestionResultBean>>> getHomeworkStudentScoreWithQuestionSingle(@Path("homework_id") String homeworkId,
                                                                                                                                @Path("class_id") String classId,
                                                                                                                                @Path("question_id") String question_id);

    /**
     * 获取某作业学生成绩列表(暂时不通, 待后台给出准确api地址)
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_STUDENT_SCORE_LIST)
    Observable<ResponseBean<String>> getHomeworkStudentScoreList(@Path("homeworkId") String homeworkId,
                                                                @Path("classId") String classId);

    /**
     * 获取典型
     *
     * @return
     */
    @GET(UrlConstant.QUESTION_SPECIAL)
    Observable<ResponseBean<List<QuestionSpecialResultBean>>> getQuestionSpecial(@Path("homeworkId") String homeworkId, @Path("questionId") String questionId);

    /**
     * 获取某个作业下某道题某个学生的答题记录
     *
     * @return
     */
    @GET(UrlConstant.SUTDENT_SINGLE_QUESTION_ANSWER)
    Observable<ResponseBean<StudentSingleQuestionAnswerResultBean>> getSingleStudentQuestionAnswer(@Path("homeworkId") String homeworkId, @Path("questionId") String questionId, @Path("studentId") String studentId);

    /**
     * 上传文件到OSS
     *
     * @return
     */
    @Multipart
    @POST(UrlConstant.UPLOAD_FILE)
    Observable<ResponseBean<OssResultBean>> uploadOss(@Part("module") RequestBody module, @Part MultipartBody.Part file);

    /**
     * 添加典型
     *
     * @return
     */

    @POST(UrlConstant.QUESTION_SPECIAL_ADD)
    Observable<ResponseBean<String>> addSpecial(@Body RequestBody requestBody);

    /**
     * 移除典型
     *
     * @return
     */

    @DELETE(UrlConstant.QUESTION_SPECIAL_DELETE)
    Observable<ResponseBean<String>> deleteSpecial(@Path("homeworkId") String homeworkId, @Path("questionId") String questionId, @Path("studCode") String studCode);


    /**
     * 获取学生作业列表(所有的 仅有名称和id返回, 非分页)
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT_ALL)
    Observable<ResponseBean<List<AnswerSheetAllDataResultBean>>> answer_sheets_all(@Path("student_id") String student_id);

    /**
     * 获取学生答题卡记录---作业详情
     *
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_ANSWERS_DETAIL_STUDENT)
    Observable<ResponseBean<List<AnswerSheetDataDetailResultBean>>> answerSheetsQuestion(@Path("answer_sheet_id") String answer_sheet_id);

    /**
     * 获取学生答题卡记录---成绩分布
     *
     * @return
     */
    @GET(UrlConstant.REPORT_DURATIONS)
    Observable<ResponseBean<List<ReportDurationsResultBean>>> report_durations(@Path("homeworkId") String homeworkId);

    /**
     * 获取某题(原题等)
     *
     * @return
     */
    @GET(UrlConstant.QUESTION_GET)
    Observable<ResponseBean<QuestionResultBean>> getQuestion(@Path("questionId") String questionId);

    /**
     * 修改选择题答案
     *
     * @return
     */
    @PATCH(UrlConstant.STUDENT_QUESTION_ANSWER_UPDATE)
    Observable<ResponseBean<String>> updateStudentQuestionAnswer(@Body RequestBody requestBody);

    /**
     * 根据题目ID获取对点微课和名师讲题
     *
     * @return
     */
    @GET(UrlConstant.QUESTION_VIDEOS)
    Observable<ResponseBean<VideoResultBean>> getVideos(@Path("questionId") String questionId);

    /**
     * 根据作业id获取班级，原题总分，学生得分
     *
     * @return
     */
    @GET(UrlConstant.CLASS_SCORE_MAXSCORE)
    Observable<ResponseBean<MaxScoreResultBean>> getClassScoreMaxScore(@Path("answer_sheet_id") String answer_sheet_id);

    /**
     * 获取某作业某班级的答题进度
     * @return
     */
    @GET(UrlConstant.ANSWER_SHEET_PROGRESS_WITH_CLASS)
    Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> answerSheetProgress(@Path("homework_id") String homework_id, @Path("class_id") String class_id);

    /**
     * 获取某个作业下某个学生的答题卡批阅进度
     * @return
     */
    @GET(UrlConstant.ANSWER_SHEET_PROGRESS_WITH_STUDENT)
    Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> answerSheetProgressWithStudent(@Path("homeworkId") String homeworkId, @Path("studId") String studId);

    /**
     * 获取某次作业某个班级学生成绩列表
     * @return
     */
    @GET(UrlConstant.HOMEWORK_QUESTION_SCORE_ALL_STUDENT)
    Observable<ResponseBean<List<AllStudentScoreResultBean>>> getHomeworkScoreStudentAll(@Path("homeworkId") String homeworkId, @Path("classId") String classId, @Path("teacherId") String teacherId);

    /**
     * 按题批阅 --- 获取该作业该班级该题所有学生批阅状态列表
     * @return
     */
    @GET(UrlConstant.MARK_LIST_WITH_QUESTION)
    Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> getMarkListByQuestion(@Path("homework_id") String homework_id,
                                                                             @Path("class_id") String class_id,
                                                                             @Path("question_id") String question_id);

    /**
     * 按人批阅时 --- 获取该作业某学生的题目列表
     * @return
     */
    @GET(UrlConstant.QUESTION_ANSWER_WITH_SINGLE_STUDENT)
    Observable<ResponseBean<List<HomeworkDetailResultBean.GradQusetionBean>>> getQustionAnswerWithSingleStudent(@Path("homework_id") String homework_id, @Path("student_id") String student_id);

    /**
     * 查看学生原卷
     * @return
     */
    @GET(UrlConstant.STUDENT_QUESTION_ANSWER_ORIGINAL)
    Observable<ResponseBean<OriginalPagerResultBean>> getStudentOriginalQuestionAnswer(@Path("homework_id") String homework_id, @Path("student_id") String student_id);

    /**
     * 查询某个教师某段时间内的错题情况。默认7天内
     * @return
     */
    @GET(UrlConstant.STATUS_ERROR_QUESTON_IN_SOME_DAY)
    Observable<ResponseBean<List<StatusErrorQuestionResultBean>>> getStatusErrorQuestionInSomeDay(@Path("teacherId") String teacherId);

    /**
     * 查询某个教师某段时间内批阅作业情况。默认昨天凌晨
     * @return
     */
    @GET(UrlConstant.STATUS_MARK_BEFORE_DAWN)
    Observable<ResponseBean<StatusHomeworkScanResultBean>> getStatusMarkBeforeDawn(@Path("teacherId") String teacherId);

    /**
     * 查询某个教师待办事项列表
     * @return
     */
    @GET(UrlConstant.TEACHER_TODO_LIST)
    Observable<AwResponseListBean<String, TeacherTodoBean>> getTeacherTodoList(@Path("teacherId") String teacherId,
                                                                               @Query("current") int current,
                                                                               @Query("size") int size);

    /**
     * 查询某个教师待办事项列表
     * @return
     */
    @GET(UrlConstant.TEACHER_CLASS_LIST)
    Observable<ResponseBean<List<TeacherClassBean>>> getTeacherClassList(@Path("teacherId") String teacherId);

    /**
     * 查询指定班级列表中的所有学生数据
     * @return
     */
    @GET(UrlConstant.CLASSES_STUDENT_LIST)
    Observable<ResponseBean<List<ClassStudentBean>>> getClassesStudentList(@Path("classIds") String classIds);

    /**
     * 批阅
     *
     * @return
     */

    @PATCH(UrlConstant.QUESTION_MARK)
    Observable<ResponseBean<String>> markQuestion(@Path("questionAnswerId") String questionAnswerId, @Body RequestBody requestBody);

    /**
     * 一周作业提交率
     */
    @POST(UrlConstant.STATISTICS_HOWEWORK_SUBMIT_RATIO)
    Observable<ResponseBean<List<StatisticsHomeworkSubmitRatioResultBean>>> getStatisticsHomeworkSubmitRatio(@Path("teacherId") String teacherId,
                                                                                                             @Body RequestBody body);

    /**
     * 一周作业错题率
     */
    @POST(UrlConstant.STATISTICS_HOWEWORK_MISTAKE_RATIO)
    Observable<ResponseBean<List<StatisticsErrorQuestionRatioResultBean>>> getStatisticsHomeworkMisstakeRatio(@Path("teacherId") String teacherId,
                                                                                                              @Body RequestBody body);

    /**
     * 一周作业错题知识点
     */
    @POST(UrlConstant.STATISTICS_HOWEWORK_ERROR_QUESTION_RATIO)
    Observable<ResponseBean<List<StatisticsErrorQuestionKnowledgeResultBean>>> getStatisticsHomeworkErrorQuestionKnowledge(@Path("teacherId") String teacherId,
                                                                                                                           @Body RequestBody body);

    /**
     * 作业提交统计表格
     */
    @POST(UrlConstant.STATISTICS_HOMEWORK_SUBMIT)
    Observable<ResponseBean<List<StatisticsHomeworkSubmitTableResultBean>>> getStatisticsHomeworkSubmitTable(@Path("teacherId") String teacherId,
                                                                                                             @Body RequestBody body);

    @GET(UrlConstant.STATISTICS_HOMEWORK_SUBMIT_NEW)
    Observable<StatisticsHomeworkSubmitTableResultBeanNew> getStatisticsHomeworkSubmitTableNew(@Path("teacherId") String teacherId,
                                                                                               @Query("classId") String classId,
                                                                                               @Query("current") int current,
                                                                                               @Query("size") int size);



    /**
     * 均分报表-某个老师作业下拉框(该接口也可不使用, 使用作业列表返回的数据筛选组合)
     */
    @POST(UrlConstant.STATISTICS_HOMEWORK_LIST)
    Observable<AwResponseListBean<AnswerSheetDataResultBean, RowsStatisticsHomeworkResultBean>> getStatisticsScoreHomeworkList(@Path("teacherId") String teacherId,
                                                                                                          @Body RequestBody body);

    /**
     * 获取某次作业所有班级均分对比,年级平均分
     */
    @GET(UrlConstant.STATISTICS_AVERAGE)
    Observable<ResponseBean<List<StatisticsScoreAverageResultBean>>> getStatisticsScoreAverage(@Path("homeworkId") String homeworkId);

    /**
     * 表统计-名次段-各班级年级名次段报表-表格
     */
    @POST(UrlConstant.STATISTICS_POSITION_RANK)
    Observable<ResponseBean<List<StatisticsScorePositionRankResultBean>>> getStatisticsScorePositionRank(@Path("homeworkId") String homeworkId,
                                                                                                         @Body RequestBody body);

    /**
     * 分数段-各班级分数段报表-表格
     */
    @POST(UrlConstant.STATISTICS_POSITION_SCORE)
    Observable<ResponseBean<List<StatisticsScorePositionScoreResultBean>>> getStatisticsScorePositionScore(@Path("homeworkId") String homeworkId,
                                                                                                           @Body RequestBody body);
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
     * 获取题目拓展
     * @return
     */
    @GET(UrlConstant.QUESTION_SIMILAR)
    Observable<ResponseBean<List<SimilarResultBean>>> getSimilar(@Path("questionId") String questionId);


    @PATCH(UrlConstant.UNCONNECT)
    Observable<NormalBean> unConnect(@Body RequestBody requestBody);

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
     * 绑定班级
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.BIND_SCHOOL_CLASS)
    Observable<ResponseBean<String>>  bindSchoolClass(@Body RequestBody requestBody);

    /**
     * 验证码登录
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.VERCODE_LOGIN)
    Observable<ResponseBean<LoginResult>> vercodeLogin(@Body RequestBody requestBody);

    /**
     * 取消绑定班级
     * @return
     */
    @PATCH(UrlConstant.UNBIND_SCHOOL)
    Observable<ResponseBean<String>>  unbindSchoolClass(@Path("teacherId")String teacherId,@Path("classId") String classId);

    /**
     * 获取教师绑定班级
     * @param teacherId
     * @return
     */
    @GET(UrlConstant.GET_CLASSES_BY_ID)
    Observable<ResponseBean<List<RequestClassesBean>>> getClassesById(@Path("teacherId") String teacherId);

    /**
     * 修改真实姓名
     * @return
     */
    @PATCH(UrlConstant.USER_UPDATE_REALNAME)
    Observable<ResponseBean<String>> updateUserRealName(@Body RequestBody requestBody);

    /**
     * 教师端扫描卷子
     * @param teacherId
     * @param templateId
     * @return
     */
    @GET(UrlConstant.GET_HOMEWORK_LIST)
    Observable<ResponseBean<List<RowsHomeworkBean>>> getHomeWorkList(@Query("teacherId") String teacherId,@Query("templateId") String templateId);

    /**
     * 获取科目信息
     * @param teacherId
     * @return
     */
    @GET(UrlConstant.GET_ERROR_COURSE)
    Observable<ResponseBean<List<ErrorCourseBean>>>  getErrorCourseList(@Path("teacherId") String teacherId);

    /**
     * 获取科目下的作业列表
     * @param teacherId
     * @param courseId
     * @return
     */
    @GET(UrlConstant.GET_ERROR_HOMEWORK)
    Observable<ResponseBean<List<ErrorHomeWork>>>  getErrorHomeWordList(@Path("teacherId") String teacherId,@Path("courseId") String courseId);

    /**
     * 获取班级数据
     * @param schoolId
     * @param templateId
     * @return
     */
    @GET(UrlConstant.GET_ERROR_CLASSES)
    Observable<ResponseBean<List<ErrorClassesBean>>>  getErrorClassesList(@Path("schoolId") String schoolId, @Path("templateId")String templateId);

    /**
     * 获取答题数据
     * @param classIds
     * @param templateIds
     * @param maxGradeRatio
     * @param minGradeRatio
     * @return
     */
    @GET(UrlConstant.GET_ERROR_MISTAKE_LIST)
    Observable<ResponseBean<List<MistakeBean>>>  getErrorMistakeList(@Query("classIds") String classIds, @Query("templateIds") String templateIds, @Query("maxGradeRatio")String maxGradeRatio, @Query("minGradeRatio") String minGradeRatio);

    /**
     * 获取题蓝数据
     * @return
     */
    @GET(UrlConstant.GET_ERROR_BASKET)
    Observable<ResponseBean<List<ErrorBasketBean>>>  getErrorBasket(@Path("teacherId") String teacherId);

    /**
     * 清空题篮
     * @param teacherId
     * @return
     */
    @GET(UrlConstant.CLEAR_ERROR_BASKET)
    Observable<ResponseBean<String>>  clearErrorBasket(@Path("teacherId") String teacherId);

    /**
     * 移除提篮
     * @return
     */
    @POST(UrlConstant.DELETE_BASKET)
    Observable<ResponseBean<String>>  deleteErrorBasket(@Body RequestBody requestBody);

    /**
     * 添加提篮
     * @param requestBody
     * @return
     */
    @POST(UrlConstant.ADD_BASKET)
    Observable<ResponseBean<String>> addErrorBasket(@Body RequestBody requestBody);

    /**
     * 选择题题目分析
     * @return
     */
    @POST(UrlConstant.GET_ERROR_STATISTICS)
    Observable<ResponseBean<ErrorChoiceStatisticsBean>>  getErrorStatistics(@Body RequestBody requestBody);

    /**
     * 主观题题目分析
     * @param questionId
     * @param homeworkIds
     * @param classIds
     * @return
     */
    @POST(UrlConstant.GET_ERROR_SUB_STATISTICS)
    Observable<ResponseBean<List<ErrorSubStatisticsBean>>> getErrorSubStatistics(@Path("questionId") String questionId, @Query("homeworkIds") String homeworkIds, @Query("classIds") String classIds);

    /**
     * 获取需讲解学生信息
     * @param homeworkId
     * @param questionId
     * @return
     */
    @GET(UrlConstant.GET_EXPLAIN_STUDENT)
    Observable<ResponseBean<List<ExplainStudentBean>>>  getExplainStudent(@Query("homeworkId") String homeworkId, @Query("questionId") String questionId);

    /**
     * 获取班级
     * @param teacherId
     * @param homeworkId
     * @return
     */
    @GET(UrlConstant.GET_EXPLAIN_CLASSES)
    Observable<ResponseBean<List<ClassesBean>>>  getExplainClasses(@Path("teacherId") String teacherId, @Path("homeworkId") String homeworkId);
}
