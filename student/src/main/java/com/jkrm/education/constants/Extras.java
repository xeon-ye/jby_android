package com.jkrm.education.constants;

import android.app.Application;
import android.os.Environment;

import com.jkrm.education.base.MyApp;
import com.sobot.chat.application.MyApplication;

/**
 * Created by hzw on 2019/5/16.
 */

public class Extras {

    public static final String IMG_URL = "imgUrl";
    public static final String COMMON_BOOLEAN = "booleanTag";
    public static final String COMMON_PARAMS = "commonParams";
    public static final String KEY_BEAN_CLASSES = "key_bean_classes";
    public static final String KEY_BEAN_MARKING = "key_bean_marking";
    public static final String KEY_BEAN_ROWS_HOMEWORK = "key_bean_rows_homework";
    public static final String KEY_BEAN_QUESTION_SIMILAR = "key_bean_question_similar";
    public static final String KEY_BEAN_QUESTION_SIMILAR_LIST = "key_bean_question_similar_list";
    public static final String KEY_BEAN_VIDEO_RESULT = "key_bean_video_result";
    public static final String KEY_TEMPLATE_ID = "templateId";
    public static final String KEY_TEMPLATE_TITLE = "templateTitle";

    public static final String KEY_COURSE_BEAN = "key_course_bean";
    public static final String KEY_COURSE_LIST = "key_course_list";

    public static final String KEY_HOMEWORK_ID = "key_homework_id";

    public static final String RESET_PWD = "reset_pwd";//重置密码标志
    public static final String MICROLESS_ID = "microless_id";//课程id
    public static final String MICROLESS_PVC_ID="microless_pvc_id";//课程pvc id
    /**
     *课程名称
     */
    public static final String MICROLESS_NAME ="microless_name";
    public static final String FILE_NAME = "file_name";

    public static final String VIDEO_GROUP_PRO = "video_group_pro";//微课目录位置
    public static final String VIDEO_CHILD_PRO = "video_child_pro";//微课视频位置

    /**
     * 下载路径
     */
    public final static String FILE_PATH = MyApp.getInstance().getExternalCacheDir() + "";
    //public final static String FILE_PATH=Environment.getExternalStorageDirectory().getPath();//获取跟目录

    /**
     * 微信支付回调区分
     */
    public final static String ORDER_PAY = "OrderPay";//订单
    public final static String RECHARGE_PAY = "RechargePay";//充值

    public final static String SUB_QUESTION = "sub_question";//组选题

    public final static String COURSE_ID = "course_id";//科目
    public final static String BATCHID = "batch_id";
    public final static String EXERCISEREPORT = "exercise_report";
    public final static String BATCHBEAN = "batch_bean";
    public final static String BATCHBEAN_SUB = "key_batch_sub";

    public final static String OUTSIDE_POS = "key_outside_pos";
    public final static String INGSIDE_POS = "key_inside_pos";

    public final static String KEY_ANS_COURSE_LIST = "key_ans_course_list";
    public final static String KEY_SIMILAR_LIST = "key_simlar_list";

    public final static String KEY_CLASSIFY = "key_classify";
    public final static String KEY_QUESTION_TYPE = "key_question_type";
    public final static String KEY_QUESTION_TIME = "key_question_time";

    public final static String KEY_VIDEO_URL = "key_video_url";
    public final static String KEY_QUESTION_ID = "key_question_id";

    public final static String KEY_INSIDEPOS = "key_insidepos";
    public final static String KEY_BATQUESTION = "key_bat_question";


    public final static String KEY_SCREEN_COURSE = "key_screen_course";
    public final static String KEY_ANSWERSHEET = "key_answersheet";
    public final static String KEY_ANSWER_SITUATION = "key_answer_situation";
    public final static String KEY_PCODE = "key_p_code";
    public final static String KEY_ADDRESS="key_address";

    public final static String KEY_REGISTER_ID="key_register_id";
    public final static String KEY_IS_VER_LOGIN="key_vercode_login";

    public final static String KEY_STEP="key_step";
    public final static String KEY_ORDER="key_order";
}

