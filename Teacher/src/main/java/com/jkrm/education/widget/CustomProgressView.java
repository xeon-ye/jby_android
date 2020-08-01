package com.jkrm.education.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwTextviewUtil;
import com.jkrm.education.R;

/**
 * Created by hzw on 2019/6/23.
 */

public class CustomProgressView extends LinearLayout {

    private Context mContext;
    private TextView tv_markYes, tv_markNot, tv_submitNot,tv_markIng;

    public CustomProgressView(Context context) {
        super(context);
        mContext = context;
    }

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.view_custom_progress, this);
        // 获取控件
        tv_markYes = findViewById(R.id.tv_markYes);
        tv_markNot = findViewById(R.id.tv_markNot);
        tv_submitNot = findViewById(R.id.tv_submitNot);
        tv_markIng = findViewById(R.id.tv_markIng);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    public void setData(int markYes, int markNot, int submitNot) {
        AwLog.d("CustomProgressView markYes: " + markYes + " ,markNot: " + markNot + " ,submitNot: " + submitNot);
        if(markYes == 0 && markNot == 0 && submitNot == 0) {
            //根据字符宽度设置显示内容
            tv_markYes.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv_markYes.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_handle_ing));
                    AwTextviewUtil.setTextViewTxt(tv_markYes, "处理中", "");
                    tv_markNot.setVisibility(View.GONE);
                    tv_submitNot.setVisibility(View.GONE);
                }
            }, 300);
            return;
        }
        if(submitNot==-1){
            submitNot=0;
        }
        int total = markYes + markNot + submitNot;
        float weightMarkYes = markYes % total;
        float weightMarkNot = markNot % total;
        float weightSubmitNot = submitNot % total;
        //设置weight
        AwLog.d("CustomProgressView weightMarkYes: " + weightMarkYes + " ,weightMarkNot: " + weightMarkNot + " ,weightSubmitNot: " + weightSubmitNot + " ,total: " + total);
        tv_markYes.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, markYes));
        tv_markNot.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, markNot));
        tv_submitNot.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, submitNot));
        //根据字符宽度设置显示内容
        int finalSubmitNot = submitNot;
        tv_markYes.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(weightMarkNot == 0 && finalSubmitNot == 0) {
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_yes_match));
                } else {
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_yes));
                }
                AwTextviewUtil.setTextViewTxt(tv_markYes, "已批阅" + markYes, String.valueOf(markYes));
            }
        }, 300);
        tv_markNot.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(markYes == 0 && finalSubmitNot == 0) {
                    tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_match));
                } else {
                    if(markYes == 0) {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_left));
                    } else if(finalSubmitNot == 0) {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_right));
                    } else {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no));
                    }
                }
                AwTextviewUtil.setTextViewTxt(tv_markNot, "批阅中" + markNot, String.valueOf(markNot));
            }
        }, 300);
        tv_submitNot.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(markYes == 0 && markNot == 0) {
                    tv_submitNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_submit_no_match));
                } else {
                    tv_submitNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_submit_no));
                }
                AwTextviewUtil.setTextViewTxt(tv_submitNot, "未提交" + finalSubmitNot, String.valueOf(finalSubmitNot));
            }
        }, 300);
    }

    public void setData(int markYes, int markNot, int submitNot,int markIng) {
        AwLog.d("CustomProgressView markYes: " + markYes + " ,markNot: " + markNot + " ,submitNot: " + submitNot);
        if(markYes == 0 && markNot == 0 && submitNot == 0&&markIng==0) {
            //根据字符宽度设置显示内容
            tv_markYes.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv_markYes.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_handle_ing));
                    AwTextviewUtil.setTextViewTxt(tv_markYes, "处理中", "");
                    tv_markNot.setVisibility(View.GONE);
                    tv_submitNot.setVisibility(View.GONE);
                    tv_markIng.setVisibility(View.GONE);
                }
            }, 300);
            return;
        }
        int total = markYes + markNot + submitNot + markIng;
        float weightMarkYes = markYes % total;
        float weightMarkNot = markNot % total;
        float weightSubmitNot = submitNot % total;
        float weightMarkIng= markIng % total;
        //设置weight
        AwLog.d("CustomProgressView weightMarkYes: " + weightMarkYes + " ,weightMarkNot: " + weightMarkNot + " ,weightSubmitNot: " + weightSubmitNot + " ,total: " + total);
        tv_markYes.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, markYes));
        tv_markIng.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT,markIng));
        tv_markNot.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, markNot));
        tv_submitNot.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, submitNot));

        //根据字符宽度设置显示内容
        tv_markYes.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(weightMarkNot == 0 && submitNot == 0) {
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_yes_match));
                } else {
                    tv_markYes.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_yes));
                }
                AwTextviewUtil.setTextViewTxt(tv_markYes, "已批阅" + markYes, String.valueOf(markYes));
            }
        }, 300);
        tv_markNot.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(markYes == 0 && submitNot == 0) {
                    tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_match));
                } else {
                    if(markYes == 0) {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_left));
                    } else if(submitNot == 0) {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_right));
                    } else {
                        tv_markNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no));
                    }
                }
                AwTextviewUtil.setTextViewTxt(tv_markNot, "未批阅" + markNot, String.valueOf(markNot));
            }
        }, 300);
        tv_submitNot.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(markYes == 0 && markNot == 0) {
                    tv_submitNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_submit_no_match));
                } else {
                    tv_submitNot.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_submit_no));
                }
                AwTextviewUtil.setTextViewTxt(tv_submitNot, "未提交" + submitNot, String.valueOf(submitNot));
            }
        }, 300);
        tv_markIng.postDelayed(new Runnable() {
            @Override
            public void run() {
               /* if(markYes == 0 && weightMarkIng == 0) {
                    tv_markIng.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_match));
                } else {
                    if(markYes == 0) {
                        tv_markIng.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_left));
                    } else if(markIng == 0) {
                        tv_markIng.setBackground(mContext.getResources().getDrawable(R.drawable.todo_progress_mark_no_right));
                    } else {*/
                        tv_markIng.setBackground(mContext.getResources().getDrawable(R.drawable.todo_condprogress_mark_no));
                /*    }
                }*/
                AwTextviewUtil.setTextViewTxt(tv_markIng, "批阅中" + markIng, String.valueOf(markIng));
            }
        },300);
    }
}
