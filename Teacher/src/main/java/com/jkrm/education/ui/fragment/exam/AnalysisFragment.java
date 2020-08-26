package com.jkrm.education.ui.fragment.exam;

import com.hzw.baselib.base.AwBaseLazyFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.R;

/**
 * @Description: 成绩分析
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 10:28
 */

public class AnalysisFragment  extends AwMvpLazyFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.analysis_layout;
    }

    @Override
    protected AwCommonPresenter createPresenter() {
        return new AwCommonPresenter();
    }
}
