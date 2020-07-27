package com.hzw.baselib.util;

import android.net.Uri;
import android.os.Environment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by hzw on 2018/12/24.
 */

public class AwTakePhotoUtil {

    public static Uri getFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "/Alvfawu/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    public static void configCompress(TakePhoto takePhoto) {
        int maxSize = Integer.parseInt("102400");//B
        int width = Integer.parseInt("400");
        int height = Integer.parseInt("400");
        boolean showProgressBar = false;
        boolean enableRawFile = true;
        CompressConfig config;
        //自带压缩
        //            config = new CompressConfig.Builder().setMaxSize(maxSize)
        //                    .setMaxPixel(width >= height ? width : height)
        //                    .enableReserveRaw(enableRawFile)
        //                    .create();
        //鲁班压缩
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    public static void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true); //使用自带相册
        builder.setCorrectImage(false); //不纠正拍照旋转角度
        takePhoto.setTakePhotoOptions(builder.create());

    }

    public static CropOptions getCropOptions() {
        int height = Integer.parseInt("400");
        int width = Integer.parseInt("400");
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);//尺寸
        //        builder.setOutputX(width).setOutputY(height);//比例
        builder.setWithOwnCrop(true);//使用自带裁剪工具
        return builder.create();
    }
}
