package com.hzw.baselib.project;

/**
 * Created by hzw on 2019/6/9.
 */

public class MarkCanvasBean {

    private float x;
    private float y;
    private float score;

    public MarkCanvasBean(float x, float y, float score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
