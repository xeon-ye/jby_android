package com.jkrm.education.bean;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/24 19:04
 */

public class GoodsDetai {
    private String goodsId;
    private String goodsName;
    private String goodsPrice;
    private String comboNum;
    private String module;
    private String goodsUrl;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getComboNum() {
        return comboNum;
    }

    public void setComboNum(String comboNum) {
        this.comboNum = comboNum;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public GoodsDetai(String goodsId, String goodsName, String goodsPrice, String comboNum, String module, String goodsUrl) {

        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.comboNum = comboNum;
        this.module = module;
        this.goodsUrl = goodsUrl;
    }
}
