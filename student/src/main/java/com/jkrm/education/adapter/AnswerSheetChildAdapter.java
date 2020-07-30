package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.MyGridView;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.rx.RxAnswerSheetType;
import com.jkrm.education.bean.rx.RxEditType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.ImgActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/29 11:13
 */

public class AnswerSheetChildAdapter extends BaseQuickAdapter<AnswerSheetBean.QuestionsBean.SubQuestionsBean, BaseViewHolder> {
    private List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> mList = new ArrayList<>();

    public AnswerSheetChildAdapter() {
        super(R.layout.answer_sheet_child_item_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, AnswerSheetBean.QuestionsBean.SubQuestionsBean item) {
        helper.setText(R.id.tv_num, item.getQuestionNum()+ "");
        GridView grid_view = helper.getView(R.id.grid_view);
        RecyclerView recyclerView = helper.getView(R.id.rcv_data_choice);
        //选择题
        if ("2".equals(item.getIsOption())) {
            helper.setVisible(R.id.ll_of_choice, true);
            helper.setVisible(R.id.grid_view, false);
            //多选题目
            if(!AwDataUtil.isEmpty(item.getType())&&!AwDataUtil.isEmpty(item.getType().getTotalId())&&"2".equals(item.getType().getTotalId())){
                AnswerSheetMultipleChoiceAdapter answerSheetMultipleChoiceAdapter=new AnswerSheetMultipleChoiceAdapter();
                AwRecyclerViewUtil.setRecyclerViewGridlayout((Activity) mContext, recyclerView, answerSheetMultipleChoiceAdapter, 6);
                setMultipleChoiceListData(item,answerSheetMultipleChoiceAdapter, recyclerView);
                if(!AwDataUtil.isEmpty(item.getStuAnswer())){
                    List<QuestionOptionBean> data = answerSheetMultipleChoiceAdapter.getData();
                    char[] chars = item.getStuAnswer().toCharArray();
                    for (QuestionOptionBean datum : data) {
                        for (char aChar : chars) {
                            if(datum.getSerialNum().equals(String.valueOf(aChar))){
                                datum.setSelect(true);
                            }
                        }

                    }

                }
                answerSheetMultipleChoiceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                        for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                            if(i==position){
                                mQuestionOptionBeanList.get(i).setSelect(!mQuestionOptionBeanList.get(i).isSelect());
                            }
                        }
                        answerSheetMultipleChoiceAdapter.notifyDataSetChanged();
                        StringBuilder mAnswer=new StringBuilder();
                        for (QuestionOptionBean questionOptionBean : mQuestionOptionBeanList) {
                            if(questionOptionBean.isSelect()){
                                mAnswer.append(questionOptionBean.getSerialNum());
                            }
                        }
                        //作答
                        EventBus.getDefault().post(new RxAnswerSheetType(item.getId(), mAnswer.toString(),true));
                    }
                });
            }else if(!AwDataUtil.isEmpty(item.getType())&&!AwDataUtil.isEmpty(item.getType().getTotalId())&&"6".equals(item.getType().getTotalId())){
                  //判断题目
                AnswerSheetChoiceAdapter onlineAnswerChoiceAdapter = new AnswerSheetChoiceAdapter();
                AwRecyclerViewUtil.setRecyclerViewGridlayout((Activity) mContext, recyclerView, onlineAnswerChoiceAdapter, 6);
                setTureOrFalseListData(onlineAnswerChoiceAdapter,recyclerView);
                if(!AwDataUtil.isEmpty(item.getStuAnswer())){
                    List<QuestionOptionBean> data = onlineAnswerChoiceAdapter.getData();
                    for (QuestionOptionBean datum : data) {
                        if(datum.getSerialNum().equals(item.getStuAnswer())){
                            datum.setSelect(true);
                        }
                    }

                }
                onlineAnswerChoiceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                        for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                            if (i == position) {
                                mQuestionOptionBeanList.get(i).setSelect(true);
                                String mAnswer = mQuestionOptionBeanList.get(i).getSerialNum();
                                //作答
                                EventBus.getDefault().post(new RxAnswerSheetType(item.getId(), mAnswer,true));
                            } else {
                                mQuestionOptionBeanList.get(i).setSelect(false);
                            }
                        }
                        onlineAnswerChoiceAdapter.notifyDataSetChanged();

                    }

                });

            }else{
                AnswerSheetChoiceAdapter onlineAnswerChoiceAdapter = new AnswerSheetChoiceAdapter();
                AwRecyclerViewUtil.setRecyclerViewGridlayout((Activity) mContext, recyclerView, onlineAnswerChoiceAdapter, 6);
                setChoiceListData(item, onlineAnswerChoiceAdapter, recyclerView);
                if(!AwDataUtil.isEmpty(item.getStuAnswer())){
                    List<QuestionOptionBean> data = onlineAnswerChoiceAdapter.getData();
                    for (QuestionOptionBean datum : data) {
                        if(datum.getSerialNum().equals(item.getStuAnswer())){
                            datum.setSelect(true);
                        }
                    }

                }
                onlineAnswerChoiceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                        for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                            if (i == position) {
                                mQuestionOptionBeanList.get(i).setSelect(true);
                                String mAnswer = mQuestionOptionBeanList.get(i).getSerialNum();
                                //作答
                                EventBus.getDefault().post(new RxAnswerSheetType(item.getId(), mAnswer,true));
                            } else {
                                mQuestionOptionBeanList.get(i).setSelect(false);
                            }
                        }
                        onlineAnswerChoiceAdapter.notifyDataSetChanged();
                    }
                });
            }
        } else {
            helper.setVisible(R.id.grid_view, true);
            helper.setVisible(R.id.ll_of_choice, false);
            List<String> mImgList = new ArrayList<>();
            mImgList.add("");
            if (!AwDataUtil.isEmpty(item.getImageList())) {
                for (String s : item.getImageList()) {
                    mImgList.add(s);
                }
            }
            CustomGridAdapter mCustomGridAdapter = new CustomGridAdapter(mContext, mImgList);
            grid_view.setAdapter(mCustomGridAdapter);
            grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == mImgList.size() - 1) {
                        PhotoFragmentDialog photoFragmentDialog = new PhotoFragmentDialog();
                        photoFragmentDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
                        photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                            @Override
                            public void photoButtonClick() {
                                //拍照并裁剪
                                PictureSelector.create((Activity) mContext).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).compress(true).rotateEnabled(true).forResult(1);
                                //作答
                                EventBus.getDefault().post(new RxAnswerSheetType(item.getId(), ""));
                            }

                            @Override
                            public void choseButtonClick() {
                                //拍照并裁剪
                                PictureSelector.create((Activity) mContext).openGallery(PictureMimeType.ofImage()).maxSelectNum(1).freeStyleCropEnabled(true).compress(true).rotateEnabled(true).forResult(1);
                                //作答
                                EventBus.getDefault().post(new RxAnswerSheetType(item.getId(), ""));
                            }

                            @Override
                            public void cancelButtonClick() {
                                photoFragmentDialog.dismiss();
                            }
                        });
                    }else{
                        Intent intent = new Intent(mContext, ImgActivity.class);
                        intent.putExtra(Extras.IMG_URL,mImgList.get(i+1));
                        mContext.startActivity(intent);
                    }

                }
            });
            mCustomGridAdapter.setOnDelelteClickListener(new CustomGridAdapter.OnDelelteClickListener() {
                @Override
                public void deleteButtonClick(int postion) {
                    if (!AwDataUtil.isEmpty(item.getImageList())) {
                        item.getImageList().remove(postion - 1);
                        notifyDataSetChanged();
                        EventBus.getDefault().post(new RxAnswerSheetType("",""));
                    }
                }
            });

        }
    }

    public void addAllData(List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if (preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }


    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(AnswerSheetBean.QuestionsBean.SubQuestionsBean bean, AnswerSheetChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        System.out.println("setChoiceListData" + mOptionsMap.toString());
        List<QuestionOptionBean> mMQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mMQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        adapter.addAllData(mMQuestionOptionBeanList);
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        adapter.disableLoadMoreIfNotFullPage(rcvData);
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setMultipleChoiceListData(AnswerSheetBean.QuestionsBean.SubQuestionsBean bean, AnswerSheetMultipleChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        System.out.println("setChoiceListData" + mOptionsMap.toString());
        List<QuestionOptionBean> mMQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mMQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        adapter.addAllData(mMQuestionOptionBeanList);
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        adapter.disableLoadMoreIfNotFullPage(rcvData);
    }

    /**
     *     判断题
     */
    private void setTureOrFalseListData( AnswerSheetChoiceAdapter adapter,RecyclerView rcvData){
        List<QuestionOptionBean> mMQuestionOptionBeanList = new ArrayList<>();
        mMQuestionOptionBeanList.add(new QuestionOptionBean("T", "正确", false));
        mMQuestionOptionBeanList.add(new QuestionOptionBean("F", "错误", false));
        adapter.addAllData(mMQuestionOptionBeanList);
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        adapter.disableLoadMoreIfNotFullPage(rcvData);
    }
}
