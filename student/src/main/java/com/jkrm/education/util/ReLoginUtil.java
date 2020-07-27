package com.jkrm.education.util;

import android.app.Activity;
import android.content.Intent;

import com.jkrm.education.base.MyApp;
import com.jkrm.education.ui.activity.login.LoginActivity;
import com.jkrm.education.ui.activity.login.PswLoginActivity;

/**
 * Created by hzw on 2019/3/1.
 */

public class ReLoginUtil {

    public static void reLogin(Activity activity) {
        MyApp.getInstance().clearLoginUser();
        activity.startActivity(new Intent(activity, PswLoginActivity.class));
        activity.finish();
    }
}
