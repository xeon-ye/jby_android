package com.jkrm.education.constants;

/**
 * Created by hzw on 2019/5/6.
 */

public class UrlConstant {

    /**
     * 调试使用MOCK-API方式.
     */
    //    private static final String MOCK_API_PREFIX = "/F1tVkxw31b70ddabb1644a448e83a9e01f3542a14b382f4";
    private static final String MOCK_API_PREFIX = "";

    /**
     * API版本前缀
     */
    private static final String COMMON_URL_PREFIX = "/api/v2/";

    /**
     * 临时接口, 获取用户账户状态
     */
    public static final String USER_JUDGE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/judge";

    /**
     * 版本更新
     */
    public static final String VERSION_CHECK = MOCK_API_PREFIX + COMMON_URL_PREFIX + "message_server/version";

    /**
     * 用户模块
     */
    public static final String USER_LOGIN = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/auth/encrypt/login";//登录
    public static final String USER_ME_INFO = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/me";//获取(GET)更新(PATCH)用户信息
    public static final String USER_UPDATE_PWD = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/password";//重置密码
    public static final String USER_UPDATE_MOBILE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update_user_mobile";//修改手机号
    public static final String USER_LOGIN_VERIFY = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/pswd/correct_error";//验证登录
    public static final String USER_INFO_BY_USERNAME = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/username/{username}";//根据用户名获取用户信息
    public static final String USER_UPDATE_NICKNAME = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/nickname";//修改用户昵称
    public static final String USER_UPDATE_AVATAR = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/avatar";//修改用户头像
    public static final String USER_UPDATE_USER_PWD = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/up/pswd";//修改用户密码

    /**
     * 作业模块
     */
    public static final String TEACHER_CLASS_COURSES_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{teacher_id}/classes_courses";//获取某个教师的班级学科列表
    public static final String HOMEWORK_QUESTION_ANSWERS_LIST_TEACHER =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{teacher_id}/homework";//获取某个教师用户的作业列表
    public static final String HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT_ALL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/{student_id}/allhomework";//获取某个学生用户全部作业列表
    public static final String CLASS_SCORE_MAXSCORE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/{answer_sheet_id}/class_score_maxscore";//根据作业id获取班级，原题总分，学生得分
    public static final String HOMEWORK_QUESTION_ANSWERS_DETAIL_STUDENT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/{answer_sheet_id}/question_answers";//获取某个学生用户的作业详情
    public static final String HOMEWORK_STUDENT_SCORE_WITH_QUESTION_SINGLE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/classes/{class_id}/question/{question_id}";//学生作答(答题详情)
    public static final String HOMEWORK_STUDENT_SCORE_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/classes/{classId}/reports/raw_grade";//作业成绩列表
    public static final String ANSWER_SHEET_PROGRESS_WITH_CLASS =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/classes/{class_id}/answer_sheet_progress";//获取某作业某班级的答题进度
    public static final String ANSWER_SHEET_PROGRESS_WITH_STUDENT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/students/{studId}/answer_sheet_progress";//获取某个作业下某个学生的答题卡批阅进度
    public static final String SUTDENT_SINGLE_QUESTION_ANSWER =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/questions/{questionId}/students/{studentId}/question_answer";//获取某个作业下某道题某个学生的答题记录
    public static final String QUESTION_MARK =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/question_answers/{questionAnswerId}";//批改某个答题记录
    public static final String QUESTION_ANSWER_WITH_SINGLE_STUDENT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/students/{student_id}/question_answers";//按人批阅时 --- 获取该作业某学生的题目列表
    public static final String TEACHER_TODO_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{teacherId}/todo";//教师待办列表
    public static final String STUDENT_QUESTION_ANSWER_ORIGINAL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/homework/{homework_id}/students/{student_id}/raw_scan";//查看学生原卷
    public static final String STUDENT_QUESTION_ANSWER_UPDATE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/update_choose_answers";//修改某选择题答案
    public static final String VIDEO_POINT_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/videos/point";//查询某次作业下的所有对点微课
    public static final String VIDEO_POINT_LIST_NEW =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/point/videos";//查询某次作业下的所有对点微课(新)
    public static final String VIDEO_POINT_LIST_BY_TEMPLATE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/templates/{template_id}/videos/point";//查询某次作业下的所有对点微课(根据模版id)
    public static final String MARK_LIST_WITH_QUESTION =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/classes/{class_id}/questions/{question_id}/grading_questions";//查询某作业某班级某道题所有学生批阅状态


    /**
     * 题库模块
     */
    public static final String QUESTION_GET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}";//获取某个题目
    public static final String QUESTION_VIDEOS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}/videos";//根据题目ID获取对点微课和名师讲题
    public static final String QUESTION_SPECIAL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/questions/homework/{homeworkId}/question/{questionId}/typical";//查看典型
    public static final String QUESTION_SPECIAL_ADD = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/typical/save";//添加典型
    public static final String QUESTION_SPECIAL_DELETE = MOCK_API_PREFIX + COMMON_URL_PREFIX +
            "product_server/product//typical/delete/{homeworkId}/{questionId}/{studCode}";//移除典型
    public static final String QUESTION_SIMILAR = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}/similar";//根据题目ID获取类题拓展(类题加练)


    /**
     * 报表模块
     */
    public static final String REPORT_DURATIONS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/reports/durations"; //班级成绩分布
    public static final String HOMEWORK_QUESTION_ANSWERS_DETAIL_TEACHER =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/classes/{classId}/reports/homework_detail";//获取某个教师用户某班级的作业详情
    public static final String HOMEWORK_QUESTION_SCORE_ALL_STUDENT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/classes/{classId}/reports/{teacherId}/student_grade_list";//获取某次作业某个班级学生成绩列表
    public static final String STATUS_ERROR_QUESTON_IN_SOME_DAY =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/reports/homework/catalog";//查询某个教师某段时间内的错题情况。默认7天内
    public static final String STATUS_MARK_BEFORE_DAWN =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/reports/homework/mark";//查询某个教师某段时间内批阅作业情况。默认昨天凌晨
    public static final String STATISTICS_HOWEWORK_SUBMIT_RATIO =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/reports/homework/submittedRatio";//一周作业提交率
    public static final String STATISTICS_HOWEWORK_ERROR_QUESTION_RATIO =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/reports/homework/knowledge";//一周作业错题知识点统计
    public static final String STATISTICS_HOWEWORK_MISTAKE_RATIO =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/reports/homework/mistakeRatio";//一周作业错题率
    public static final String STATISTICS_HOMEWORK_SUBMIT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/homework/classes/statistical_scores_table";//作业统计-作业提交统计-表格
    public static final String STATISTICS_HOMEWORK_SUBMIT_NEW = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/homework/classes/statistical_scores_table/page";//获取某个教师用户的作业列表 新

    public static final String STATISTICS_HOMEWORK_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/homework/average/drop_downbox";//均分报表-某个老师作业下拉框
    public static final String STATISTICS_AVERAGE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/classes/reports/average_deviation";//获取某次作业所有班级均分对比,年级平均分
    public static final String STATISTICS_POSITION_SCORE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/classes/reports/score_section_table";//分数段-各班级分数段报表-表格
    public static final String STATISTICS_POSITION_RANK =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/classes/reports/rank_table";//报表统计-名次段-各班级年级名次段报表-表格


    /**
     * 文件模块
     */
    public static final String UPLOAD_FILE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "file_server/sys_file/upload_oss";//上传文件至OSS并返回URL

    /**
     * 教务模块
     */
    public static final String CLASSES_STUDENT_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/schools/classes/{classIds}/students";//已选择的班级下的所有学生列表
    public static final String TEACHER_CLASS_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/schools/teachers/{teacherId}/classes";//某教师下所有班级

    /**
     * 短信模块
     */
    public static final String GET_PHONE_CODE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "util_server/sms/get_code";//获取短信验证码
    public static final String VERIFY_PHONE_CODE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "util_server/sms/check_code";//验证短信验证码


    /**
     * 识别模块
     */
    public static final String TEACHER_RESOLVE_PROGRESS =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "resolve_server/resolve/{teacherId}/progress";//查询某位教师的作业识别进度

    /**
     * 取消挂接
     */
    public static final String UNCONNECT = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/answer_sheet_unconnect";

    /**
     * 选择三级联动地址
     */
    public static final String GET_REGIONS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/dim/front/regions";

    /**
     * 学校列表
     */
    public static final String GET_SCHOOL_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "/sch_mgmt_server/schools/front/page";

    /**
     * 用户注册
     */
    public static final String USER_REGISTER = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/auth/register";

    /**
     * 获取学段以及科目
     */
    public static final String GET_PERIOD_COURSE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/attribute/value/other_relas/all_list/jby20200712000000regiter/cascade";

    /**
     * 绑定学校对应的账号
     */
    public static final String BIND_SCHOOL_CLASS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/auth/register/bind/school/class";

    /**
     * 补全用户信息  绑定学校教育教务属性
     */
    public static final String COMPLETION_INFO = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/auth/register/bind/school";

    /**
     * 获取近3年学校班级 get请求
     */
    public static final String GET_CLASSES = MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/schools/{schoolId}/{userId}/classes/near3year";

    /**
     * 验证码登录
     */
    public static final String VERCODE_LOGIN = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/auth/encrypt/login/code";

    /**
     * 取消班级绑定
     */
    public static final String UNBIND_SCHOOL = MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/school/{teacherId}/unbind/{classId}";

    /**
     * 获取绑定得班级
     */
    public static final String GET_CLASSES_BY_ID = MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/schools/teachers/{teacherId}/classes";

    /**
     * 修改用户真实姓名
     */
    public static final String USER_UPDATE_REALNAME = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/realname";

    /**
     * 教师端 扫答题卡
     */
    public static final String GET_HOMEWORK_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/template/teacher/scan";

    /**
     * 获取科目信息
     */
    public static final String GET_ERROR_COURSE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{teacherId}/course";

    /**
     * 获取科目下的作业列表
     */
    public static final String GET_ERROR_HOMEWORK = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{teacherId}/course/{courseId}/home";
    /**
     * 获取班级数据
     */
    public static final String GET_ERROR_CLASSES = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/teachers/{schoolId}/template/{templateId}";

    /**
     * 数据题目数据
     * classIds={班级id（多个班级逗号拼接）}&templateIds={templateId}}&maxGradeRatio={最大得分率}}&minGradeRatio={最小得分率}
     */
    public static final String GET_ERROR_MISTAKE_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/mistake_list";

    /**
     * 选择题数据分析
     */
    public static final String GET_ERROR_STATISTICS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/reports/templat/statistics";

    /**
     * 主观题数据分析
     */
    public static final String GET_ERROR_SUB_STATISTICS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/reports/{questionId}/theme_chart";

    /**
     * 添加题蓝
     */
    public static final String ADD_BASKET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/add/basket";
    /**
     * 移除题蓝
     */
    public static final String DELETE_BASKET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/rm/basket";
    /**
     * 获取题蓝数据
     */
    public static final String GET_ERROR_BASKET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{teacherId}/basket";
    /**
     * 清空题蓝
     */
    public static final String CLEAR_ERROR_BASKET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/clear/{teacherId}/basket";
    /**
     * //导出题篮
     */
    public static final String QUESTION_BASKET_EXPORT_CUSTOM = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/exportQuestions";
    /**
     * 获取需讲解学生信息
     */
    public static final String GET_EXPLAIN_STUDENT = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/explain/students";
    /**
     * 获取班级
     */
    public static final String GET_EXPLAIN_CLASSES = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/teacher/{teacherId}/homework/{homeworkId}/classes";

    /**
     * 动态获取系统安全码
     */
    public static final String GET_SAFE_CODE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "system_server/sys/safe/code";

    /**
     * 退出登录
     */
    public static final String LOGOUT = MOCK_API_PREFIX + COMMON_URL_PREFIX + "system_server/logout";

    /**
     * 阅卷任务列表
     */
    public static final String GET_EXAM_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/app/examList/{teacherId}";

    /**
     * 阅卷任务详情
     */
    public static final String GET_REVIEW_TASK_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/readTask/{teacherId}";

    /**
     * 回评查询分数
     */
    public static final String GET_EXAM_REVIEW_SCORE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examReviewScore/{teacherId}";

    /**
     * 回评评分列表数据
     */
    public static final String GET_EXAM_REVIEWLIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examReviewList/{teacherId}";

    /**
     * 批阅表头  readWay //1单凭，2双评，3终评
     */
    public static final String GET_EXAM_READ_HEADER = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examReadHeader/{teacherId}";

    /**
     * 批阅列表
     */
    public static final String GET_EXAM_READ_QUESTIONS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examReadQuestions/{teacherId}";

    /**
     * 考试批阅
     */
    public static final String EXAM_MARK = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/updatExamQuestion";

    /**
     * 回评批阅表头题号
     */
    public static final String GET_EXAM_REVIEW_HEADER=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examReviewHeader/{teacherId}";

    /**
     * 回评列表
     */
    public static final String GET_EXAM_REVIEW_QUESTIONS=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examReviewQuestions/{teacherId}";

    /**
     * 成绩分析列表
     */
    public static final String GET_ANALYSIS_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examAnalysisPage";


    public static final String GET_OVER_VIEW=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem//pc/examCourse/results/overview";
    /**
     * 柱状图
     */
    public static final String GET_COLUMN_DATA=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examCourse/results/analysis";
    /**
     * 折线图
     */
    public static final String GET_LINE_DATA=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examCourse/history/five";

    /**
     * 获取考试年级列表
     */
    public static final String GET_GRADE_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"sch_mgmt_server/querySchoolGrade";

    /**
     * 获取考试班级列表
     */
    public static final String GET_CLASS_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"sch_mgmt_server/schools/teachers/{teacherId}/classes";

    /**
     * 获取科目列表
     */
    public static final String GET_CLASS_SUBJECT=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/teachers/{teacherId}/course";

    /**
     * 综合成绩表
     */
    public static final String MULTIPLE_TABLE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examCompre/hensivePage";
    /**
     * 班级成绩对比表
     */
    public static final String CLASS_TABLE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examClass/results/contrast";
    /**
     * 小题得分表
     */
    public static final String SCORE_TABLE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examCourse/item/question";
    /**
     * 成绩分段表
     */
    public static final String SECTION_TABLE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examClass/results/onAscalePage";
    /**
     * 成绩分段表(获取成绩)
     */
    public static final String SECTION_TABLE_SCORE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examClass/results/onAscale/total";
    /**
     * 学生名单详情表
     */
    public static final String STU_INFO_TABLE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "testPaper_server/clem/pc/examClass/results/onAscale/studPage";
    /**
     * 获取学生分析科目
     */
    public static final String GET_COURSE_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/clem/pc/examCompre/oneCourse";
    /**
     * 获取考试下学科
     */
    public static final String GET_EXAM_COURSE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"testPaper_server/exam/new/exam/course";

    /**
     * 获取考试试卷
     */
    public static final String GET_EXAM_QUESTION=MOCK_API_PREFIX+COMMON_URL_PREFIX+"product_server/questions/exam/{questionId}";

    /**
     * 获取角色列表
     */
    public static final String GET_ROLE_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/role/sys/role/getUserRolesById";
}
