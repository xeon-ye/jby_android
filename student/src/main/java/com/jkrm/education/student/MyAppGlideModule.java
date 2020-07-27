package com.jkrm.education;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 10:21
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
