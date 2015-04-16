package com.qz.lifehelper.persist;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 持续化p2p相关的数据
 */
@EBean
public class P2PPersist {

    @RootContext
    Context context;

    /**
     * 获取p2p分类目录信息
     */
    public String getP2PCategory() throws IOException {
        InputStream p2pCategoryIS = context.getAssets().open("p2p_category");
        return IOUtils.toString(p2pCategoryIS);
    }
}
