package com.qz.lifehelper.utils;

import java.util.Random;

/**
 * 离线服务器工具类
 */
public class OutlineServiceUtil {

    /**
     * 模拟网络加载
     */
    public static void analogLoding() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(2000) + 500);
    }

}
