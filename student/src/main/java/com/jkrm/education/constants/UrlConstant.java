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
    public static final String USER_GET_USER_INFO = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/me";//获取用户信息
    public static final String USER_TEACHER_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "sch_mgmt_server/teachers/students/{stuId}";//根据学生id获取教师用户列表
    public static final String USER_UPDATE_PWD = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/password";//修改密码
    public static final String USER_UPDATE_MOBILE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update_user_mobile";//修改手机号
    public static final String USER_LOGIN_VERIFY = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/pswd/correct_error";//验证登录
    public static final String USER_INFO_BY_USERNAME = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/username/{username}";//根据用户名获取用户信息
    public static final String USER_UPDATE_NICKNAME = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/nickname";//修改用户昵称
    public static final String USER_UPDATE_AVATAR = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/update/avatar";//修改用户头像
    public static final String USER_UPDATE_USER_PWD = MOCK_API_PREFIX + COMMON_URL_PREFIX + "user_server/user/up/pswd";//修改用户密码

    /**
     * 作业模块
     */
        public static final String HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/{student_id}/answer_sheets";//获取某个学生用户的作业列表
    public static final String HOMEWORK_QUESTION_ANSWERS_LIST_STUDENT_ALL = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/{student_id}/allhomework";//获取某个学生用户全部作业列表
    public static final String HOMEWORK_QUESTION_ANSWERS_LIST_SUBJECT = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/homework/student/{studentId}/error/course";//获取学生错误科目列表
    public static final String HOMEWORK_QUESTION_ANSWERS_DETAIL_STUDENT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/{answer_sheet_id}/question_answers";//获取某个学生用户的作业详情

    public static final String MISTAKE_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/students/{studentId}/mistake_list";//获取错题本
    public static final String MISTAKE_LIST_NEW = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/students/{student_id}/mistake_list";//获取错题本(改版)
    public static final String CLASS_SCORE_MAXSCORE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/{answer_sheet_id}/class_score_maxscore";//根据作业id获取班级，原题总分，学生得分
    public static final String VIDEO_POINT_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homework_id}/videos/point";//查询某次作业下的所有对点微课
    public static final String VIDEO_POINT_LIST_NEW =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/{homeworkId}/point/videos";//查询某次作业下的所有对点微课(新)
    public static final String VIDEO_POINT_LIST_BY_TEMPLATE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/homework/templates/{template_id}/videos/point";//查询某次作业下的所有对点微课(根据模版id)
    public static final String PRACTICE_TABLE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/practice/{studendId}/week";//练习题一周练习题数统计(柱图)
    public static final String PRACTICE_EXERCISES =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/students/practice/{studendId}/exercises";//练习题一周练习题统计
    public static final String ERROR_QUESTION_STATISTICS_LIST =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/template/{templateId}/questions";//错题统计列表
    public static final String TEMPLATE_INFO =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/template/{templateId}/name";//获取模板信息


    /**
     * 题库模块
     */
    public static final String QUESTION_GET = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}";//获取某个题目
    public static final String QUESTION_VIDEOS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}/videos";//根据题目ID获取对点微课和名师讲题
    public static final String QUESTION_SIMILAR = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/{questionId}/similar";//根据题目ID获取类题拓展(类题加练)

    public static final String QUESTION_BASKET_DEL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/practice/delete/{questionId}/{studentId}";//移除题篮
    public static final String QUESTION_BASKET_DEL_ALL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/practice/remove/{studentId}";//清空题篮
    public static final String QUESTION_BASKET_ADD =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/practice/save";//添加题篮
    public static final String QUESTION_BASKET_EXPORT =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/exportQuestions";//导出题篮
    public static final String QUESTION_BASKET_EXPORT_CUSTOM = "api/v2/product_server/questions/exportQuestions";//导出题篮
    public static final String ERROR_QUESTION_SAVE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/mistakes/save";//添加错题
    public static final String ERROR_QUESTION_DELETE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/product/mistakes/delete/{questionId}/{studentId}";//删除错题

    /**
     * 报表模块
     */
    public static final String REPORT_DURATIONS = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/reports/durations"; //班级成绩分布
    public static final String REPORT_QUESTIONSCORE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/student/{studentId}/reports/questionScore"; //作业得分
    public static final String REPORT_QUESTIONSCORERATE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "report_server/homework/{homeworkId}/student/{studentId}/reports/questionScoreRate"; //作业得分率

    /**
     * 短信模块
     */
    public static final String GET_PHONE_CODE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "util_server/sms/get_code";//获取短信验证码
    public static final String VERIFY_PHONE_CODE =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "util_server/sms/check_code";//验证短信验证码

    /**
     * 文件模块
     */
    public static final String UPLOAD_FILE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "file_server/sys_file/upload_oss";//上传文件至OSS并返回URL


    /**
     * 图书习题模块
     */
    public static final String GET_BOOK_EXERCISES_ALL_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/attribute/value/other_relas/all_list/d7328874709e6ec15070dadcdea141b0/cascade";//获取筛选内容（学段 品牌 年份等）
    //课时
    public static final String GET_CLASS_HOUR_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/catalog/category/d7328874709e6ec15070dadcdea141b0/value/{class_hour}";
    //习题列表
    public static final String GET_BOOK_MAIN_LIST = MOCK_API_PREFIX + COMMON_URL_PREFIX + "product_server/questions/list_questions_by_catalog_id/stud_id/{class_hour_key}";
    //获取题号和得分
    public static final String GET_TOPIC_SCORE = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/exercise_questions/{class_hour_key}";
    //获取课时id 和微课视频 key
    public static final String GET_LESSON_HOUR = MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/template/{class_hour_key}";
    /**
     * 微课视频
     */
    public static final String GET_COURSE_TYPE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"resource_server/combo/type/list";
    //属性
    public static final String GET_COURSE_ATTR=MOCK_API_PREFIX+COMMON_URL_PREFIX+"product_server/attribute/value/other_relas/all_list/a77a95ec292d743b5607669456050e8a/cascade";
    //微课套餐列表
    public static final String GET_COURSE_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"resource_server/combo/manage/app/list";

    //某个微课目录和微课
    public static final String GET_COURSE_PLAY_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"resource_server/combo/calalog/pc/video/play";

    //创建订单
    public static final String CREATE_ORDER=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/order/save";
    //微信支付
    public static final String WECHAT_PAY=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/wx/app/stud/weixinPay";
    //支付宝支付
    public static final String ALI_PAY=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/alipay/createAppOrder";
    //用户账户余额
    public static final String GET_ACCOUNT_BALANCES=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/user/points/{user_id}";
    //钱包支付
    public static final String PURSE_PAY=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/wx/pointPay";
    //获取教育充值金额
    public static final String GET_INVEST_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/order/money/list";
    //获取播放记录
    public static final String GET_WATCH_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/member/watch/log/list";
    //上传用户观看记录
    public static final String SAVE_WATCH_LOG=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/member/watch/log/save";
    public static final String STUDENT_QUESTION_ANSWER_ORIGINAL =
            MOCK_API_PREFIX + COMMON_URL_PREFIX + "mark_server/answer_sheets/homework/{homework_id}/students/{student_id}/raw_scan";//查看学生原卷

    public static final String COURSE_SHARE_URL="http://www.baidu.com";//微课分享链接

    //类题加练-学生练习保存接口
    public static final String REINFORCE_SAVE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/save";
    //获取答题记录
    public static final String GET_BATCH=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/bacthId/{batchid}";
    //获取答题情况
    public static final String GET_BATCH_QUESTION=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/question/{batchid}";


    public static final String COLLECT_QUESTON=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/question/collect/save";//收藏
    public static final String REMOVE_COLLECT_QUESTON=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/question/collect/remove";//取消收藏
    public static final String READOVER_QUESTON=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/update/read";//批阅

    public static final String  REINFORCE_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/list";//答题记录
    public static final String  REINFORCE_COURSE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/reinforce/course/{studId}";//答题科目

    //错题本首页接口
    public static final String GET_ERROR_QUESTION_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/errors/course/{studentId}";
    //错题本详情 --分类
    public static final String GET_BY_CLASSIFY=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/errors/classify";

    //错题本 --时间
    public static final String GET_BY_TIME=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/errors/time";

    //错题本 分页
    public static final String GET_BY_TIME_PAGED=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/errors/time";
    //错题列表
    public static final String GET_ERROR_DETAIL=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/students/errors/detail";
    //答题卡
    public static final String GET_ANSWER_SHEET=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/template/questions";

    //答题卡提交
    public static final String SUBMIT_ANSWER_SHEET=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/mark/questions/online";
    //题目讲解
    public static final String EXPLAIN_QUESTION=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/mark/explain/{value}";
    //添加错题本
    public static final String ADD_MISTAKE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"mark_server/homework/add/mistake";
    /**
     * 选择三级联动地址
     */
    public static final String GET_REGIONS=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/dim/front/regions";

    /**
     *学校列表
     */
    public static final String GET_SCHOOL_LIST=MOCK_API_PREFIX+COMMON_URL_PREFIX+"/sch_mgmt_server/schools/front/page";

    /**
     * 用户注册
     */
    public static final String USER_REGISTER=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/auth/register";

    /**
     * 补全用户信息  绑定学校教育教务属性
     */
    public static final String COMPLETION_INFO=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/auth/register/bind/school";

    /**
     * 获取学段以及科目
     */
    public static final String GET_PERIOD_COURSE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"product_server/attribute/value/other_relas/all_list/jby20200712000000regiter/cascade";

    /**
     * 绑定学校对应的账号
     */
    public static final String BIND_SCHOOL_CLASS=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/auth/register/bind/school/class";

    /**
     * 获取近3年学校班级 get请求
     */
    public static final String GET_CLASSES=MOCK_API_PREFIX+COMMON_URL_PREFIX+"sch_mgmt_server/schools/{schoolId}/{userId}/classes/near3year";

    /**
     * 验证码登录
     */
    public static final String VERCODE_LOGIN=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/auth/encrypt/login/code";

    /**
     * 获取学生绑定班级
     */
    public static final String GET_CLASSES_BY_ID=MOCK_API_PREFIX+COMMON_URL_PREFIX+"sch_mgmt_server/school/{studId}/students/classes";


    /**
     * 修改用户真实姓名
     */
    public static final String USER_UPDATE_REALNAME=MOCK_API_PREFIX+COMMON_URL_PREFIX+"user_server/user/update/realname";

    /**
     * 动态获取系统安全码
     */
    public static final String GET_SAFE_CODE=MOCK_API_PREFIX+COMMON_URL_PREFIX+"system_server/sys/safe/code";
}
