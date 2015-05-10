package com.qz.lifehelper.persist;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 负责持续集成离线数据
 */
@EBean
public class OutlinePersist {

    @RootContext
    Context context;

    /**
     * 获取公告信息
     */
    public String getNoticeInfo() {
        String noticeInfo = null;
        try {
            InputStream noticeInfoIS = context.getAssets().open("notice_info");
            noticeInfo = IOUtils.toString(noticeInfoIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return noticeInfo;
    }

    /**
     * 获取十大旅游景点的json数据
     */
    public String getTenTopSpots() {
        String tenTopSpots = null;
        try {
            InputStream tenTopSpotsIS = context.getAssets().open("ten_top_spots");
            tenTopSpots = IOUtils.toString(tenTopSpotsIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tenTopSpots;
    }

    /**
     * 获取P2P items的json数据
     */
    public String getP2PItems() {
        String p2pItemsJson = null;
        try {
            InputStream tenTopSpotsIS = context.getAssets().open("p2p_items");
            p2pItemsJson = IOUtils.toString(tenTopSpotsIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p2pItemsJson;
    }

    /**
     * 获取十大餐厅的json数据
     */
    public String getTenTopRestruant() {
        String tenTopRestruant = null;
        try {
            InputStream tenTopSpotsIS = context.getAssets().open("ten_top_restruant");
            tenTopRestruant = IOUtils.toString(tenTopSpotsIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tenTopRestruant;
    }
}
