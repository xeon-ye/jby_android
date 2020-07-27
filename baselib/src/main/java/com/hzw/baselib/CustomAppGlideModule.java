package com.hzw.baselib;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @Description: glide 4.0 升级
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 11:21
 */
@GlideModule
public class CustomAppGlideModule  extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return true;
    }
}
