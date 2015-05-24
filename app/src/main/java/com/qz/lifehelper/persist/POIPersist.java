package com.qz.lifehelper.persist;

import android.content.Context;

import com.qz.lifehelper.entity.POICategoryBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 持续化POI用到的数据
 */
@EBean
public class POIPersist {

    @RootContext
    Context context;

    static Map<String, String> poiCategories = new HashMap<>();

    static {
        poiCategories.put("餐厅", "restruant");
        poiCategories.put("医院", "hospital");
        poiCategories.put("酒店", "hotel");
        poiCategories.put("保洁", "cleaner");
        poiCategories.put("咖啡", "coffe");
        poiCategories.put("银行", "bank");
        poiCategories.put("烧烤", "bbq");
        poiCategories.put("月嫂", "moon");
        poiCategories.put("物流", "move");
        poiCategories.put("电影", "movie");
        poiCategories.put("超市", "supermarket");
    }

    /**
     * 获取默认的POI图片
     *
     * @param categoryBean poi类别
     */
    public String getDefaultPOIImage(POICategoryBean categoryBean) {
        String category = poiCategories.get(categoryBean.categotyName);
        if (category == null) {
            return "file:///android_asset/ten_top_spots_image/ten_top_spots_1.jpg";
        }
        String image = "file:///android_asset/poi/" + category + "/" + String.valueOf(new Random().nextInt(11) % 10) + ".png";
        return image;
    }

    /**
     * 获取默认的POI细节
     *
     * @param categoryBean poi类别
     */
    public String getDefaultPOIDetail(POICategoryBean categoryBean) {
        String category = poiCategories.get(categoryBean.categotyName);
        String detail = null;
        InputStream detailIS = null;
        try {
            detailIS = context.getAssets().open("poi/" + category + "/" + String.valueOf(new Random().nextInt(11) % 10) + ".txt");
            detail = IOUtils.toString(detailIS);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return detail;
        }
    }
}
