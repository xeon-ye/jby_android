package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 11:18
 */

public class RxOnlineJumpType {

    private int OutSidePageNum;//外部 viewpager 滑动位置
    private int InSidePageNum;//内部 viewpager 滑动位置

    public RxOnlineJumpType(int outSidePageNum, int inSidePageNum) {
        OutSidePageNum = outSidePageNum;
        InSidePageNum = inSidePageNum;
    }

    public int getOutSidePageNum() {
        return OutSidePageNum;
    }

    public void setOutSidePageNum(int outSidePageNum) {
        OutSidePageNum = outSidePageNum;
    }

    public int getInSidePageNum() {
        return InSidePageNum;
    }

    public void setInSidePageNum(int inSidePageNum) {
        InSidePageNum = inSidePageNum;
    }
}
