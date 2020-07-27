package com.hzw.baselib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hzw.baselib.R;
import com.hzw.baselib.project.MarkCanvasBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzw on 2019/5/26.
 */

public class AwImgUtil {

    /**
     * 自定义默认加载中及失败后的图片
     *
     * @param mContext
     * @param v
     * @param url
     * @param resId
     */
    public static void setImgWithCustom(Context mContext, ImageView v, Object url, int resId) {
       /* Glide.with(mContext).load(url).
                asBitmap().
                placeholder(resId).
                error(resId).
                into(v);*/

    }

    /**
     * 默认图片加载及失败图片
     *
     * @param mContext
     * @param v
     * @param url
     */
    public static void setImg(Context mContext, ImageView v, Object url) {
//        Glide.with(mContext).load(url).
//                placeholder(mContext.getResources().getDrawable(R.mipmap.icon_img_load_ing)).
//                error(mContext.getResources().getDrawable(R.mipmap.icon_img_load_error)).
//                into(v);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_img_load_ing)
                .error(R.mipmap.icon_img_load_error);
        Glide.with(mContext).load(url).apply(requestOptions).into(v);
    }

    /**
     * 默认头像加载及失败图片
     *
     * @param mContext
     * @param v
     * @param url
     */
    public static void setImgAvatar(Context mContext, ImageView v, Object url) {
        /*Glide.with(mContext).load(url).
                asBitmap().
                placeholder(mContext.getResources().getDrawable(R.mipmap.icon_img_load_ing)).
                error(mContext.getResources().getDrawable(R.mipmap.touxiang)).
                into(v);*/
        Glide.with(mContext).load(url).apply(new RequestOptions().error(R.mipmap.touxiang)).into(v);
    }

    /**
     * 回调监听图片加载, 使用默认头像加载及失败图片
     *
     * @param mContext
     * @param target
     * @param url
     */
    public static void setImgToTarget(Context mContext, SimpleTarget target, Object url) {
    /*    Glide.with(mContext).load(url).
                asBitmap().
                placeholder(mContext.getResources().getDrawable(R.mipmap.icon_img_load_ing)).
                error(mContext.getResources().getDrawable(R.mipmap.icon_img_load_error)).
                into(target);*/
    }


    /**
     * drawable to bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * drawable to bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap2(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 灰度处理
     *
     * @param bmSrc
     * @return
     */
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    /**
     * 左1右2上3下4
     */
    public static void setDrawableDirection(Context context, TextView tv, int tag, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (tag) {
            case 1:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 4:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public static void cancelDrawableDirection(Context context, TextView tv) {
        tv.setCompoundDrawables(null, null, null, null);
    }

    /**
     * https://blog.csdn.net/oAiTan/article/details/50517629
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    public static Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap getViewBitmap(View view, int width, int height) {
        if (view == null) {
            return null;
        }
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        view.draw(canvas);
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,bitmapNew width: " + bitmap.getWidth()  + " ,btimapNew height: " + bitmap.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());


        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,width,height, true);
        Canvas canvas2 = new Canvas(bitmap2);
        Paint paint = new Paint();
        //针对绘制bitmap添加抗锯齿
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        /**
         * 对canvas设置抗锯齿的滤镜，防止变化canvas引起画质降低
         */
        canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        //new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())
        canvas2.drawBitmap(bitmap, null, new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), paint);
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,bitmap2 width: " + bitmap2.getWidth()  + " ,bitmap2 height: " + bitmap2.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());

        return bitmap;
    }

    public static Bitmap getViewBitmapMatrix(View view, int width, int height) {
        if (view == null) {
            return null;
        }
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        view.draw(canvas);
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,bitmapNew width: " + bitmap.getWidth()  + " ,btimapNew height: " + bitmap.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());


        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,width,height, true);
        Canvas canvas2 = new Canvas(bitmap2);
        Paint paint = new Paint();
        //针对绘制bitmap添加抗锯齿
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        /**
         * 对canvas设置抗锯齿的滤镜，防止变化canvas引起画质降低
         */
        canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        Matrix matrix = new Matrix();
        matrix.postScale(AwConvertUtil.divFloat(width, view.getWidth(), 5), AwConvertUtil.divFloat(height, view.getHeight(), 5), 0, 0);
        canvas2.drawBitmap(bitmap, matrix, paint);
        paint.setColor(Color.RED);
        paint.setTextSize(30f);
        canvas2.drawText("哈哈哈哈哈", 200, 100, paint);
        canvas2.save();
        canvas2.restore();
        //        canvas2.drawText("测试", 0, 0, 50, 50, paint);
        AwLog.d("图片上传 save后 width: " + width + " ,height: " + height + " ,bitmap2 width: " + bitmap2.getWidth()  + " ,bitmap2 height: " + bitmap2.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());

        return bitmap2;
    }

    public static Bitmap getViewBitmapMatrixReset(ImageView view, Bitmap bitmap, List<MarkCanvasBean> list) {
        if (bitmap == null || view == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());
        float scaleWidth = AwConvertUtil.divFloat(width, view.getWidth(), 5);
        float scaleHeight = AwConvertUtil.divFloat(height, view.getHeight(), 5);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
//        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,width,height, true);
        Canvas canvas2 = new Canvas(bitmap2);
        Paint paint = new Paint();
        //针对绘制bitmap添加抗锯齿
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        /**
         * 对canvas设置抗锯齿的滤镜，防止变化canvas引起画质降低
         */
        canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas2.drawBitmap(bitmap, null, new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), paint);
        paint.setColor(Color.RED);// 红色画笔
        paint.setTextSize(60f);
        for(MarkCanvasBean tempBean : list) {
            canvas2.drawText("+" + AwConvertUtil.rvZeroAndDot(String.valueOf(tempBean.getScore())), tempBean.getX() * scaleWidth, tempBean.getY() * scaleHeight, paint);
        }
        canvas2.save();
        canvas2.restore();
        //        Matrix matrix = new Matrix();
//        matrix.postScale(AwConvertUtil.divFloat(width, view.getWidth(), 5), AwConvertUtil.divFloat(height, view.getHeight(), 5), 0, 0);
//        canvas2.drawBitmap(bitmap, matrix, paint);
//        paint.setColor(Color.RED);
//        paint.setTextSize(30f);
//        canvas2.drawText("哈哈哈哈哈", 200, 100, paint);
//        canvas2.save();
//        canvas2.restore();
        //        canvas2.drawText("测试", 0, 0, 50, 50, paint);
        AwLog.d("图片上传 save后 width: " + width + " ,height: " + height + " ,bitmap2 width: " + bitmap2.getWidth()  + " ,bitmap2 height: " + bitmap2.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());

        return bitmap2;
    }

    public static Bitmap getViewBitmapWithScale(ImageView view, int width, int height, List<MarkCanvasBean> list) {
        if (view == null) {
            return null;
        }
        Bitmap bitmapNew =((BitmapDrawable)view.getDrawable()).getBitmap();
        float x = width/bitmapNew.getWidth();
        float y = height/bitmapNew.getHeight();
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,bitmapNew width: " + bitmapNew.getWidth()  + " ,btimapNew height: " + bitmapNew.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight() + " ,x: " + x + " ,y: " + y);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        compressScale(bitmap, width, height);
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawARGB(255, 0, 255, 0);
        canvas.drawBitmap(bitmapNew, new Rect(0, 0, bitmapNew.getWidth(), bitmapNew.getHeight()), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Paint());
        Paint paint = new Paint();
        paint.setColor(Color.RED);// 红色画笔
        for(MarkCanvasBean tempBean : list) {
            canvas.drawText("+" + AwConvertUtil.rvZeroAndDot(String.valueOf(tempBean.getScore())), tempBean.getX() * x, tempBean.getY() * y, paint);
        }
//        canvas.scale(x, y);
//        view.draw(canvas);
//        bitmap = Bitmap.createScaledBitmap(bitmap,width,height, true);
//        compressScale(bitmap, width, height);
//        compressBitmapToGivenWidthAndHeight(bitmap, width, height);
        return bitmap;
    }

    public static Bitmap getViewBitmapMarked(ImageView view, int width, int height) {
        if (view == null) {
            return null;
        }
        Bitmap bitmapNew =((BitmapDrawable)view.getDrawable()).getBitmap();
        AwLog.d("图片上传 width: " + width + " ,height: " + height + " ,bitmapNew width: " + bitmapNew.getWidth()  + " ,btimapNew height: " + bitmapNew.getHeight() +
                " ,imageview width: " + view.getWidth() + " ,imageview height: " + view.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmapNew, new Rect(0, 0, bitmapNew.getWidth(), bitmapNew.getHeight()), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Paint());
        return bitmap;
    }

    public static Bitmap compressBitmapToGivenWidthAndHeight(Bitmap bitmap, double newWidth, double newHeight) {


        //获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();


        //创建操作图片用的matrix对象
        Matrix matrix = new Matrix();


        //计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;


        //压缩Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);

        return newBitmap;
    }

    public static void imageSave(final Bitmap bitmap, final String name, Subscriber<String> subscriber) {
        AwLog.d("imageSave name: " + name);
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (bitmap == null) {
                    subscriber.onError(new NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"));
                } else {
                    try {
                        File parentFile = new File(Environment.getExternalStorageDirectory() + File.separator + "jby");
                        if (!parentFile.exists()) {
                            parentFile.mkdir();
                        }
                        File imageFile = new File(parentFile, name + ".png");
                        if (!imageFile.exists()) {
                            imageFile.createNewFile();
                        } else {
                            imageFile.delete();
                            imageFile.createNewFile();
                        }
                        FileOutputStream outStream;
                        outStream = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outStream);
                        subscriber.onNext(imageFile.getPath());
                        subscriber.onCompleted();
                        outStream.flush();
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public static void imageSaveTemp(final Bitmap bitmap, final String name, Subscriber<String> subscriber) {
        AwLog.d("imageSave name: " + name);
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (bitmap == null) {
                    subscriber.onError(new NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"));
                } else {
                    try {
                        File parentFile = new File(Environment.getExternalStorageDirectory() + File.separator + "jby/temp");
                        if (!parentFile.exists()) {
                            parentFile.mkdir();
                        }
                        File imageFile = new File(parentFile, name + ".png");
                        if (!imageFile.exists()) {
                            imageFile.createNewFile();
                        } else {
                            imageFile.delete();
                            imageFile.createNewFile();
                        }
                        FileOutputStream outStream;
                        outStream = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outStream);
                        subscriber.onNext(imageFile.getPath());
                        subscriber.onCompleted();
                        outStream.flush();
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    /**
     * 质量压缩方法
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath, float ww, float hh) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按比例大小压缩方法
     * @param image （根据Bitmap图片压缩）
     * @return
     */
    public static Bitmap compressScale(Bitmap image, float ww, float hh) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;
//        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        //return bitmap;
    }
}
