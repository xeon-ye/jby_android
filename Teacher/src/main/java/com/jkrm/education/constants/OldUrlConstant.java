package com.jkrm.education.constants;

/**
 * Created by hzw on 2019/4/25.
 */

public class OldUrlConstant {

    //方法
    public static final String METHOD_UPDATEPASSWORD = "updatePassword.api";
    public static final String METHOD_TEACHER = "register/teacher.api";//老师注册
    public static final String METHOD_GETUSERBASEINFO = "user/getUserInfo.api";//获取用户信息
    public static final String METHOD_UPDATEUSERINFO = "user/updateUserInfo.api";//完善个人信息
    public static final String METHOD_SECONDLIST = "basi/secondList.api";//获取学段
    public static final String METHOD_GETAREA = "area/getAreas.api";//获取地区
    public static final String METHOD_GETSCHOOL = "school/getSchool.api";//获取学校

    //公共部分
    public static final String METHOD_LOGIN ="login.api";//登录
    public static final String METHOD_GETSDICVERSION ="mine/getSDicVersion.api"; //获取版本号、下载地址
    public static final String METHOD_PHONE = "registerAuth/phone.api";//获取验证码

    public static final String METHOD_GETSUBJECTSINFO="speak/getSubjectsInfo.api";//科目列表
    public static final String METHOD_GETSUBJECTSVERSION="speak/getSubjectsVersion.api";//科目下的版本
    public static final String METHOD_GETSUBJECTSVERSIONNEXT="speak/getSubjectsVersionNext.api";//版本下的模块（必修1、必修2）
    public static final String METHOD_GETCHAPTERS="prepClassify/getChapters.api";//章节
    public static final String METHOD_GETCHAPTERCLASSINFO="prepClassify/getChapterClassInfo.api";//获取章节下的课时
    public static final String METHOD_GETPREPHIGHHOWORKCLASSLIST="prepHighStud/getPrepHighHoworkClassList.api";//教师下的班级

    //【步步高】【讲卷】
    public static final String METHOD_PREPHIGHHOWORK_GETCLASSHOWORKS= "prepHighHowork/getClassHoworks.api";//获取步步高作业
    public static final String METHOD_GETHIGHSTUDSCORELIST = "speak/getHighStudScoreList.api";//获取成绩分布列表
    public static final String METHOD_GETPREPHIGHHOWORKNOPAPER ="speak/getPrepHighHoworkNopaper.api";//查看未交作业名单
    public static final String METHOD_GETHIGHSTUDSCORECONTRASTLIST = "speak/getHighStudScoreContrastList.api";//获取小题得分对比列表
    public static final String METHOD_GETPAPERANALYSIS ="speak/getPaperAnalysis.api";//整卷分析数据
    public static final String METHOD_GETHIGHSTUDACHIEVEMNETLIST ="speak/getHighStudAchievementList.api";//查看成绩详细信息
    public static final String METHOD_GETCLASSSCOREREPORT="speak/getClassScoreReport.api";//.讲卷---成绩报表
    public static final String METHOD_GETQUESTIONDETAILS ="speak/getQuestionDetails.api";//试题详情
    public static final String METHOD_GETTYPICALQUESTIONS ="speak/getTypicalQuestions.api";//典型卷详情
    public static final String METHOD_GETCHOICESTUDENTS ="speak/getChoiceStudents.api";//选择该选项的同学名称
    public static final String METHOD_GETHOWORKSTUDENTS ="speak/getHoworkStudents.api";//获取该班级试卷的所有同学名
    public static final String METHOD_GETERRORPERQUESLIST ="speak/getErrorPerQuesList.api";//错误率
    //【步步高】【阅卷】
    public static final String METHOD_GETSTUDDETANUMLIST = "mark/getStudDetaNumList.api";//获取题组列表
    public static final String METHOD_GETNEXTHIGHSTUDDETA = "mark/getNextHighStudDeta.api";//下一个(下一人)
    public static final String METHOD_GETLASTHIGHSTUDDETA = "mark/getLastHighStudDeta.api";    //阅卷 - 上翻页
    public static final String METHOD_GETSTUDDETABYSTUDENTCODE = "mark/getStudDetaBystudentCode.api";//阅卷 - 点击学生名查看--根据学生号、题号获取 阅卷信息
    public static final String METHOD_SAVEHIGHSTUDDETA ="mark/saveHighStudDeta.api";//保存批改作业信息
    public static final String METHOD_ASIGNTYPICAL = "mark/asignTypical.api";    //阅卷 - 添加典型题
    public static final String  METHOD_GETDETAQUESNUMBYHOWORKCODE="mark/getDetaQuesNumByHoworkCode.api";//获取题号
    public static final String METHOD_GETPHSDLISTBYQUESNUM = "mark/getPhsdListByQuesNum.api";//获取试题列表（回评）
    public static final String METHOD_GETPHSTUDBYSTUDNUM = "mark/getPHStudByStudNum.api";//批阅、回评 - 查看整卷列表
    public static final String METHOD_GETHSTUDDETABYSTUD = "mark/getHStudDetaByStud.api";//根据题号获取试题

    //【名校】【讲卷】
    public static final String METHOD_PREPELITEHOWORK_GETCLASSHOWORKS="prepEliteHowork/getClassHoworks.api";//获取步步名校作业
    public static final String METHOD_GETELITESTUDSCORELIST="speak/getEliteStudScoreList.api";//成绩分布接口
    public static final String METHOD_GETPREPELITEHOWORKNOPAPER="speak/getPrepEliteHoworkNopaper.api";//查看未交作业名单
    public static final String METHOD_GETELITESTUDSCORECONTRASTLIST = "speak/getEliteStudScoreContrastList.api";//获取小题得分对比列表
    public static final String METHOD_GETELITEPAPERANALYSIS ="speak/getElitePaperAnalysis.api";//整卷分析数据
    public static final String METHOD_GETELITESTUDACHIEVEMNETLIST ="speak/getEliteStudAchievementList.api";//查看成绩详细信息
    public static final String METHOD_GETELITECLASSSCOREREPORT="speak/getEliteClassScoreReport.api";//.讲卷---成绩报表
    public static final String METHOD_GETELITEQUESTIONDETAILS ="speak/getEliteQuestionDetails.api";//试题详情
    public static final String METHOD_GETELITETYPICALQUESTIONS ="speak/getEliteTypicalQuestions.api";//典型卷详情
    public static final String METHOD_GETELITECHOICESTUDENTS ="speak/getEliteChoiceStudents.api";//选择该选项的同学名称
    public static final String METHOD_GETELITEHOWORKSTUDENTS ="speak/getEliteHoworkStudents.api";//获取该班级试卷的所有同学名
    public static final String METHOD_GETELITEERRORPERQUESLIST ="speak/getEliteErrorPerQuesList.api";//错误率
    public static final String METHOD_GETPREPELITEHOWORKCLASSLIST = "prepEliteStud/getPrepEliteHoworkClassList.api";//获教师--作业下的班级

    //【名校】【阅卷】
    public static final String METHOD_GETELITESTUDDETANUMLIST="mark/getEliteStudDetaNumList.api";
    public static final String METHOD_GETNEXTELITESTUDDETA = "mark/getNextEliteStudDeta.api";//下一个(下一人)
    public static final String METHOD_GETLASTELITESTUDDETA = "mark/getLastEliteStudDeta.api";//阅卷 - 上翻页
    public static final String METHOD_GETELITESTUDDETABYSTUDENTCODE = "mark/getEliteStudDetaBystudentCode.api";//阅卷 - 点击学生名查看--根据学生号、题号获取 阅卷信息
    public static final String METHOD_SAVEELITESTUDDETA ="mark/saveEliteStudDeta.api";//保存批改作业信息
    public static final String METHOD_ELITEASIGNTYPICAL = "mark/eliteAsignTypical.api";    //阅卷 - 添加典型题
    public static final String  METHOD_GETELITEDETAQUESNUMBYHOWORKCODE="mark/getEliteDetaQuesNumByHoworkCode.api";//获取题号
    public static final String METHOD_GETELITELISTBYQUESNUM = "mark/getEliteListByQuesNum.api";//获取试题列表（回评）
    public static final String METHOD_GETELSTUDBYSTUDNUM = "mark/getElStudByStudNum.api";//批阅、回评 - 查看整卷列表
    public static final String METHOD_GETESTUDDETABYSTUD = "mark/getEStudDetaByStud.api";//根据题号获取试题

    //【合并】【讲卷】
    public static final String METHOD_GETQUESTIONVIDEO = "wrongQuestion/getQuestionVideo.api";//微课视频推送
    public static final String METHOD_POINTTOREMEDYTEACHER = "wrongQuestion/pointToRemedyTeacher.api";//错题本----对点补救
    public static final String METHOD_GETCHECKSTUDENT = "speak/getCheckStudent.api";//讲卷主观题查看名单增加颜色区分

    //【讲卷】未识别答题卡
    public static final String METHOD_LISTERRORINFO = "omrData/listErrorInfo.api";//获取未识别答题卡
    //【讲卷】步步高、校本作业绑定
    public static final String METHOD_ANALYZEHHDATA = "prepHighStud/analyzeHighHoworkData.api";//步步高
    public static final String METHOD_ANALYZEEHDATA = "prepEliteStud/analyzeEliteHoworkData.api";//校本
    //【讲卷】获取未识别答题卡详细信息
    public static final String METHOD_GETOMRDATAINFO = "omrData/getOmrDataInfo.api";//未识别答卷详情页

    //【合并】【个人中心】
    public static final String METHOD_GETUSERROLE = "mine/getUserRole.api";//角色判断
    public static final String METHOD_GETHOMEWORKCHARGE = "mine/getHomeworkCharge.api";//班主任角色
    public static final String METHOD_GETCHARGESTATISTICSAPP = "mine/getChargeStatisticsApp.api";//班主任角色统计查询
    public static final String METHOD_GETHOMEWORKNAVIGATION ="mine/getHomeworkNavigation.api";//校长\年级主任\备课组长
    public static final String METHOD_GETHOMEWORKSTATISTICS = "mine/getHomeworkStatistics.api";//校长\年级主任\备课组长作业统计查询

    //【考试】
    public static final String METHOD_GETSTUDYYEAR = "examTeacCorrTask/getStudyYear.api";//学年列表
    public static final String METHOD_GETTASKRECORDS = "examTeacCorrTask/getTaskRecords.api";//考试列表
    public static final String METHOD_GETTEACHERTASKRECORDS = "examTeacCorrTask/getTeacherTaskRecords.api";//试卷批改列表_3.1.0

    //【考试】【批阅】
    public static final String METHOD_REVIEWPROGRESS = "examTeacQuesCorrTask/reviewProgress.api";// 试题批阅进度
    public static final String METHOD_GETCORRECTQIDS = "examTeacQuesCorrTask/getCorrectQids.api";//获取批改题号列表
    public static final String METHOD_GETNOCORRECTFIRST = "examTeacQuesCorrTask/getNoCorrectFirst.api";//点击题号获取该教师第一位
    public static final String METHOD_GETNOCORRECTBEFOREAFETER = "examTeacQuesCorrTask/getNoCorrectBeforeAfeter.api";//获取上一位 - 获下一位
    public static final String METHOD_UPDATECORRINFO = "examTeacQuesCorrTask/updateCorrInfo.api";//更新批阅信息--点击保存

    public static final String METHOD_CORRECTEDLIST = "examTeacQuesCorrTask/correctedList.api";//获取已批改集合
    public static final String METHOD_REVIEWPAPER = "examTeacQuesCorrTask/reviewPaper.api";//回评
    public static final String METHOD_GETCORRECTBEFOREAFETER = "examTeacQuesCorrTask/getCorrectBeforeAfeter.api";//获取上一位 - 获下一位
    public static final String METHOD_UPDATEREVIEWPAPER = "examTeacQuesCorrTask/updateReviewPaper.api";//回评批阅保存

    public static final String METHOD_UPDATEREPRESENTATIVE = "examTeacQuesCorrTask/updateRepresentative.api";//设置典型卷
    public static final String METHOD_UPDATETASKSTATUS = "examTeacQuesCorrTask/updateTaskStatus.api";//提交批阅

    //【考试】【讲卷】
    public static final String METHOD_GETQUESTIONFOREXAM = "examPaperQues/getQuestionForExam.api";//获取试题详细信息
    public static final String METHOD_QUERYEXAMCLASSINFO = "examInfo/queryExamClassInfo.api";//获取考试下班级信息
    public static final String METHOD_GETSCOREDISTRBUTE = "report/examinee/getScoreDistribute.api";//成绩分布
    public static final String METHOD_QUESCOMMCOURGETLIST = "quesCommCour/getlist.api";//小题得分对比
    public static final String METHOD_QUESALLSTATSWITHPAGE = "report/ques/ques_all_stats_with_page.api";//获取考试下的试题列表
    public static final String METHOD_QUESWITHOPTIONS = "report/ques/ques_with_option.api";//选择题/判断题）各选项的统计
    public static final String METHOD_QUESTERMBYISCORRECT = "report/ques/ques_term_by_is_correct.api";//（填空题）正确/错误统计
    public static final String METHOD_QUESTERMBYRATIORANGE = "report/ques/ques_term_by_ratio_range.api";//（解答题/选做题）得分率区间统计

    public static final String METHOD_QUERYSUBJECTSCOREREPORTFORAPP = "report/paper/querySubjectScoreReportForApp.api";//考试成绩单报表
    public static final String METHOD_QUERYSUBJECTSTUDREPORTFORAPP = "report/paper/querySubjectStudReportForApp.api";//查看原始成绩

    public static final String METHOD_GETSCORINGRATERANGE = "report/examinee/getScoringRateRange.api";//题号得分率范围-试题列表
    public static final String METHOD_SELECTQUESFORTBYEXAMQUESIDS ="examQuesRepo/selectQuesForTByExamQuesIds.api";//随机获取对应题库试题
    public static final String METHOD_QUERYREQPRESENTATIVELIST ="report/paper/queryRepresentativeList.api";//查看典型卷详情
}
