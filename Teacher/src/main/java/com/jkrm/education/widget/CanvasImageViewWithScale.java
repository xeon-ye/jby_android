package com.jkrm.education.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.project.MarkCanvasBean;
import com.jkrm.education.bean.rx.RxMarkImgOperateType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/6/9.
 */

@SuppressLint("AppCompatCustomView")
public class CanvasImageViewWithScale extends ImageView implements OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final int DEFAULT_INVALID_SCORE = -1;
    public static final int STUATUS_NORMAL = 0;
    public static final int STUATUS_ONPAUSE = 1;
    public static final int STUATUS_ONRESUME = 2;
    public static final int STUATUS_RELOAD = 3;
    private float clickX;
    private float clickY;
    private float maxScore;
    private float score = DEFAULT_INVALID_SCORE;
    // 画笔－－写字
    private Paint mTextPaint = new Paint();
    private List<MarkCanvasBean> mScoreList = new ArrayList<>();
    private IMarkScoreListener mIMarkScoreListener;
    private Context mContext;
    private boolean enable;
    private int originWidth;
    private int originHeight;
    private int currentStuatus = 0;
    // 检测两个手指在屏幕上做缩放的手势工具类
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mMatrix;//矩阵记录变化信息

    public void setFristLoad(boolean fristLoad) {
        isFristLoad = fristLoad;
    }

    //首次加载,避免onGlobalLayout多次执行
    private boolean isFristLoad = true;
    //最大倍数
    private static float MAX_SCALE = 4.0F;
    private static float MIN_SCALE = 1.0F;



    public CanvasImageViewWithScale(Context context) {
        super(context);
        this.mContext = context;
        init();

    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
        // 绘制文字
        mTextPaint.setColor(Color.RED);// 红色画笔
        mTextPaint.setTextSize(AwDisplayUtil.dip2px(mContext, 20));// 设置字体大小
        originWidth = getWidth();
        originHeight = getHeight();
        mScaleGestureDetector = new ScaleGestureDetector(mContext, this);
        mScaleGestureDetector.setQuickScaleEnabled(false);
    }

    public CanvasImageViewWithScale(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();

    }

    public CanvasImageViewWithScale(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        AwLog.d("drawScore onDraw..............");
        drawScore(canvas);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!enable || score == DEFAULT_INVALID_SCORE) {
            AwLog.d("onTouchEvent !enable || score == DEFAULT_INVALID_SCORE");
            mGestureDetector2.onTouchEvent(event);//滑动
            mScaleGestureDetector.onTouchEvent(event);//缩放手势
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AwLog.d("onTouchEvent ACTION_DOWN ");
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            AwLog.d("onTouchEvent ACTION_MOVE ");
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            AwLog.d("onTouchEvent ACTION_UP ");
            //获取坐标
//            clickX = event.getX();
//            clickY = event.getY();
//            AwLog.d("onTouchEvent drawScore onTouchEvent clickX: " + clickX + " ,clickY: " + clickY);
//            invalidate();
        }
        //无论如何都处理各种外部手势
        mGestureDetector.onTouchEvent(event);
        return true;
    }


    /**
     * 常用手势处理
     * <p>
     * 在onTouchEvent末尾被执行.
     */
    private GestureDetector mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //只有在单指模式结束之后才允许执行fling
            AwLog.e("onTouchEvent GestureDetector onFling ");
            return true;
        }

        public void onLongPress(MotionEvent e) {
            AwLog.d("onTouchEvent GestureDetector onLongPress ");

        }

        public boolean onDoubleTap(MotionEvent e) {
            AwLog.d("onTouchEvent GestureDetector onDoubleTap ");
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            AwLog.d("onTouchEvent GestureDetector onSingleTapConfirmed ");
            //获取坐标
            clickX = e.getX();
            clickY = e.getY();
            AwLog.d("onTouchEvent GestureDetector  drawScore onTouchEvent clickX: " + clickX + " ,clickY: " + clickY);
            invalidate();
            //触发点击
            return true;
        }
    });

    private GestureDetector mGestureDetector2 = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //只有在单指模式结束之后才允许执行fling
            AwLog.d("onTouchEvent mGestureDetector2 onFling ");
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            AwLog.e("mGestureDetector2  onScrolling");
            checkBorderAndCenterWhenScale();
            mMatrix.postTranslate(-distanceX, -distanceY);
            setImageMatrix(mMatrix);
            return true;
        }

        public void onLongPress(MotionEvent e) {
            AwLog.d("onTouchEvent mGestureDetector2 onLongPress ");

        }

        public boolean onDoubleTap(MotionEvent e) {
            AwLog.d("onTouchEvent mGestureDetector2 onDoubleTap ");
//            doubleTapOperate();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            AwLog.d("onTouchEvent mGestureDetector2 onSingleTapConfirmed ");
            EventBus.getDefault().postSticky(new RxMarkImgOperateType(false, "", true));
            return true;
        }
    });


    private void doubleTapOperate() {
        if (getMeasuredWidth() < originWidth * 2) {
            this.setMeasuredDimension(originWidth * 2, originHeight * 2);
        } else {
            this.setMeasuredDimension(originWidth, originHeight);
        }
        invalidate();
    }

    /**
     * 设置分数
     *
     * @param score
     */
    public void setScore(float score, float maxScore) {
        this.score = score;
        this.maxScore = maxScore;
    }

    public void setListenter(IMarkScoreListener listenter) {
        this.mIMarkScoreListener = listenter;
    }

    public void resumeDraw() {

    }

    public void setEnableTouch(boolean enable) {
        this.enable = enable;
    }

    private void drawScore(Canvas canvas) {
        if (canvas != null) {
            if (mTextPaint != null) {
                if (score == DEFAULT_INVALID_SCORE && AwDataUtil.isEmpty(mScoreList)) {
                    AwLog.d("drawScore 分数-1且集合为空");
                } else {
                    if (score == DEFAULT_INVALID_SCORE) {
                        AwLog.d("drawScore 分数是-1或者集合不是空 ===重新绘制已有绘制操作");
                        for (MarkCanvasBean tempBean : mScoreList) {
                            canvas.drawText("+" + AwConvertUtil.rvZeroAndDot(String.valueOf(tempBean.getScore())), tempBean.getX(), tempBean.getY(), mTextPaint);
                        }
                        String totalScore = getTotalScore();
                        AwLog.d("drawScore 分数是-1或者集合不是空 === 重新绘制已有绘制操作 postSticky totalScore: " + totalScore);
                        EventBus.getDefault().postSticky(new RxMarkImgOperateType(true, totalScore, false));
                        return;
                    }
                    AwLog.d("drawScore 分数不是-1或者集合不是空");
                    if (STUATUS_NORMAL == currentStuatus) {
                        AwLog.d("drawScore 分数不是-1或者集合不是空 === normal正常操作");
                        float currentTotalScore = 0f;
                        for (MarkCanvasBean tempBean : mScoreList) {
                            currentTotalScore += tempBean.getScore();
                        }
                        if (currentTotalScore == maxScore && currentTotalScore + score - maxScore > 0) {//8 8 maxScore 12
                            Toast.makeText(mContext, "已达最大分数", Toast.LENGTH_SHORT).show();
                        } else {
                            if (currentTotalScore + score - maxScore > 0) {
                                score = maxScore - currentTotalScore;
                            }
                            mScoreList.add(new MarkCanvasBean(clickX, clickY, score));
                        }
                        if (mScoreList.size() > 2) {
                            //TODO 此处有异常, 会在同一位置出现分数, 去掉最后一次非手动添加的分数绘制
                            if (mScoreList.get(mScoreList.size() - 1).getX() == mScoreList.get(mScoreList.size() - 2).getX()) {
                                mScoreList.remove(mScoreList.size() - 1);
                            }
                        }
                        for (MarkCanvasBean tempBean : mScoreList) {
                            canvas.drawText("+" + AwConvertUtil.rvZeroAndDot(String.valueOf(tempBean.getScore())), tempBean.getX(), tempBean.getY(), mTextPaint);
                        }
                        String totalScore = getTotalScore();
                        AwLog.d("drawScore 分数不是-1或者集合不是空 === normal正常操作 postSticky totalScore: " + totalScore);
                        EventBus.getDefault().postSticky(new RxMarkImgOperateType(true, totalScore, false));
                    } else if (STUATUS_ONPAUSE == currentStuatus) {
                        AwLog.d("drawScore 分数不是-1或者集合不是空 === onPause什么也不操作");
                    } else if (STUATUS_ONRESUME == currentStuatus) {
                        AwLog.d("drawScore 分数不是-1或者集合不是空 === onResume重新绘制已有绘制操作");
                        for (MarkCanvasBean tempBean : mScoreList) {
                            canvas.drawText("+" + AwConvertUtil.rvZeroAndDot(String.valueOf(tempBean.getScore())), tempBean.getX(), tempBean.getY(), mTextPaint);
                        }
                        String totalScore = getTotalScore();
                        AwLog.d("drawScore 分数不是-1或者集合不是空 === onResume重新绘制已有绘制操作 postSticky totalScore: " + totalScore);
                        EventBus.getDefault().postSticky(new RxMarkImgOperateType(true, totalScore, false));
                    }

                }

            } else {
                AwLog.d("drawScore mTextPaint is null");
            }
        } else {
            AwLog.d("drawScore canvas is null");
        }
    }

    public List<MarkCanvasBean> getCanvasList() {
        return mScoreList;
    }

    public void setStuatus(int status) {
        this.currentStuatus = status;
    }

    public void drawAutoMarkResult(float[] xys) {
        currentStuatus = STUATUS_NORMAL;
        clickX = xys[0];
        clickY = xys[1];
        invalidate();
    }

    /**
     * 获取已批阅的总分
     *
     * @return
     */
    public String getTotalScore() {
        if (AwDataUtil.isEmpty(mScoreList)) {
            return "";
        }
        float totalScore = 0;
        for (MarkCanvasBean tempBean : mScoreList) {
            totalScore += tempBean.getScore();
        }
        return String.valueOf(totalScore);
    }

    /**
     * 清空评分
     */
    public void clearAllScore() {
        mScoreList = new ArrayList<>();
        invalidate();
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        AwLog.e("onTouchEvent scale onScale");
        float scale = getScale();
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        AwLog.e("多点触控时候的缩放值: " + scaleFactor);
        if (getDrawable() == null) {
            return true;
        }

        //缩放范围的控制, 放大时需要小于最大，缩小时需要大于最小
        if ((scale < MAX_SCALE && scaleFactor > 1.0f) || (scale > MIN_SCALE && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < MIN_SCALE) {
                scaleFactor = MIN_SCALE / scale;
            }

            if (scale * scaleFactor > MAX_SCALE) {
                scaleFactor = MAX_SCALE / scale;
            }
            AwLog.e("设置最终缩放值 " + scaleFactor);
            mMatrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        AwLog.e("onTouchEvent scale onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        AwLog.e("onTouchEvent scale onScaleEnd");
        float oldDis = scaleGestureDetector.getPreviousSpan();
        float curDis = scaleGestureDetector.getCurrentSpan();
        if (oldDis - curDis > 50) {
            //small
            AwLog.e("缩小");
        } else if (oldDis - curDis < -50) {
            //max
            AwLog.e("放大");
        }
    }

    @Override
    public void onGlobalLayout() {
        if (isFristLoad) {
            if (null == getDrawable()) {
                return;
            }
            //图片宽高
            int originWidth = getDrawable().getIntrinsicWidth();
            int originHeight = getDrawable().getIntrinsicHeight();
            //控件宽高
            int viewWidth = getWidth();
            int viewHeight = getHeight();

            float scale = 0;
            // 图片宽高都大于控件宽高
            if (originWidth > viewWidth && originHeight > viewHeight ||
                    originWidth < viewWidth && originHeight < viewHeight) {
                scale = Math.min((float) viewWidth / originWidth, (float) viewHeight / originHeight);
            } else if (originWidth > viewWidth) { // 图片宽大于控件宽
                scale = viewWidth * 1.0f / originWidth;
            } else { // 图片高大于控件高
                scale = viewHeight * 1.0f / originHeight;
            }
            MIN_SCALE=scale;
            MAX_SCALE=scale*4;
            AwLog.e("onGlobalLayout" + scale);
            mMatrix.setScale(scale, scale, viewWidth / 2, viewHeight / 2);
            mMatrix.preTranslate((viewWidth - originWidth) / 2, (viewHeight - originHeight) / 2);
            setImageMatrix(mMatrix);
            isFristLoad = false;

        }
    }

    public interface IMarkScoreListener {
        void markTotalScore(int score);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //需要在这里添加监听。
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }


    /**
     * 在缩放的时候进行边界以及我们的位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 缩放时进行边界检测，防止出现白边
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        /**
         * 如果宽度或高度小于空间的宽或者高，则让其居中
         */
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }

        mMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mMatrix);
    }

    /**
     * 获得图片放大缩小以后的宽和高，以及left，right，top，bottom
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }


}
