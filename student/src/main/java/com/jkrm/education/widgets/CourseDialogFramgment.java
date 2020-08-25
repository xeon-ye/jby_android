package com.jkrm.education.widgets;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.FileUtils;
import com.hzw.baselib.util.MemoryTool;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.CourseSuccessBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.ui.activity.CourseBroadcastActivity;
import com.sobot.chat.utils.ToastUtil;
import com.sobot.chat.widget.kpswitch.view.emoticon.EmoticonPageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/9 17:38
 */

public class CourseDialogFramgment extends DialogFragment {

    private Button mBtndelete;
    private List<CoursePlayResultBean> mGroupValues = new ArrayList<>();
    private List<List<CoursePlayResultBean.VideoListBean>> mChildValues = new ArrayList<>();
    CoursePlayAdapter mCoursePlayAdapter;
    private ExpandableListView mEpv;
    private ConfirmListener mConfirmListener;
    private TextView mTvAll;

    public ConfirmListener getConfirmListener() {
        return mConfirmListener;
    }

    public void setConfirmListener(ConfirmListener confirmListener) {
        mConfirmListener = confirmListener;
    }

    public interface ConfirmListener {
        void onClickComplete(List<CoursePlayResultBean.VideoListBean> mChildValues);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments && !AwDataUtil.isEmpty(arguments.getSerializable(Extras.KEY_COURSE_LIST))) {
            mGroupValues = (List<CoursePlayResultBean>) arguments.getSerializable(Extras.KEY_COURSE_LIST);
            for (int i = 0; i < mGroupValues.size(); i++) {
                mChildValues.add(mGroupValues.get(i).getVideoList());
            }

        }
    }

    private void init(View view) {
        mBtndelete = view.findViewById(R.id.btn_delete);
        mEpv = view.findViewById(R.id.elv);
        mTvAll = view.findViewById(R.id.tv_all);
        mBtndelete.setText("确定缓存");
        mBtndelete.setTextColor(getResources().getColor(R.color.colorAccent));
        mBtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmListener confirmListener = (ConfirmListener) getActivity();
                List<CoursePlayResultBean.VideoListBean> list = new ArrayList<>();
                for (List<CoursePlayResultBean.VideoListBean> childValue : mChildValues) {
                    for (CoursePlayResultBean.VideoListBean videoListBean : childValue) {
                        if (videoListBean.isChecked()) {
                            list.add(videoListBean);
                        }
                    }
                }
                if (list.isEmpty()) {
                    ToastUtil.showLongToast(getActivity(), "请选择课程");
                    return;
                }
               /* if(null!=mConfirmListener){
                    mConfirmListener.onClickComplete(list);
                }*/

                confirmListener.onClickComplete(list);
                dismiss();
            }
        });
        mCoursePlayAdapter = new CoursePlayAdapter(mGroupValues, mChildValues);
        mEpv.setAdapter(mCoursePlayAdapter);
        for (int i = 0; i < mCoursePlayAdapter.getGroupCount(); i++) {
            mEpv.expandGroup(i);
        }
        CheckBox checkBox = view.findViewById(R.id.cb_all);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (List<CoursePlayResultBean.VideoListBean> childValue : mChildValues) {
                        if (null != childValue && childValue.size() > 0) {

                            for (CoursePlayResultBean.VideoListBean videoListBean : childValue) {
                                videoListBean.setChecked(true);
                            }
                        }
                    }
                } else {
                    for (List<CoursePlayResultBean.VideoListBean> childValue : mChildValues) {
                        if (null != childValue && childValue.size() > 0) {

                            for (CoursePlayResultBean.VideoListBean videoListBean : childValue) {
                                videoListBean.setChecked(false);
                            }
                        }
                    }
                }
                getChoseNum();
                mCoursePlayAdapter.notifyDataSetChanged();
            }
        });
        TextView tvFreeSize = view.findViewById(R.id.tv_free_size);
        tvFreeSize.setText("剩余空间"+ MemoryTool.getAvailableInternalMemorySize(getActivity()));

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.course_dialog_fragment_layout, null);
        init(view);
        final Dialog dialog = new Dialog(getActivity(), R.style.style_dialog);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    class CoursePlayAdapter extends BaseExpandableListAdapter {

        private List<CoursePlayResultBean> mGroupValues;
        private List<List<CoursePlayResultBean.VideoListBean>> mChildValues;

        public CoursePlayAdapter(List<CoursePlayResultBean> groupValues, List<List<CoursePlayResultBean.VideoListBean>> childValues) {
            mGroupValues = groupValues;
            mChildValues = childValues;
        }

        @Override
        public int getGroupCount() {
            return mGroupValues.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mGroupValues.get(i).getVideoList().size();
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }


        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(getActivity(), R.layout.couse_cache_dialog_group_item_layout, null);

            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(mGroupValues.get(i).getTitle());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            view = View.inflate(getActivity(), R.layout.course_cache_dialog_item_layout, null);
            CoursePlayResultBean.VideoListBean videoListBean = mChildValues.get(i).get(i1);
            CheckBox checkBox = view.findViewById(R.id.cb);
            ImageView img_down = view.findViewById(R.id.img_down);
            TextView tvName = view.findViewById(R.id.tv_name);
            TextView tvShow = view.findViewById(R.id.tv_time);
            TextView tv_time_and_size = view.findViewById(R.id.tv_time_and_size);

            //数据库查询是否下载过
            DaoVideoBean daoVideoBean = new DaoVideoBean();
            daoVideoBean.setId(videoListBean.getId());
            DaoVideoBean daoVideoBean1 = DaoUtil.getInstance().queryVideoByUrl(daoVideoBean);
            //时长 大小
            String times = videoListBean.getTimes();
            String[] split = times.split(":");
            tv_time_and_size.setText(times);
            tvName.setText(videoListBean.getName());
            if (null != daoVideoBean1) {
                checkBox.setEnabled(false);
                checkBox.setVisibility(View.GONE);
                img_down.setVisibility(View.VISIBLE);
            }
            tvShow.setText(videoListBean.getTimes());
            checkBox.setChecked(videoListBean.isChecked());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        videoListBean.setChecked(true);
                    } else {
                        videoListBean.setChecked(false);
                    }
                    getChoseNum();
                }
            });
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }

    private void getChoseNum() {
        int num = 0;
        for (List<CoursePlayResultBean.VideoListBean> childValue : mChildValues) {
            for (CoursePlayResultBean.VideoListBean videoListBean : childValue) {
                if (videoListBean.isChecked()) {
                    num++;
                }
            }
        }
        mTvAll.setText("全选（"+num+"）");
    }

}
