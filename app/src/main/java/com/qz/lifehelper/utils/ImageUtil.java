package com.qz.lifehelper.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 用户处理图片的工具
 */
@EBean
public class ImageUtil {

    /**
     * 图片的最大宽度
     */
    static private final int maxWidth = 720;

    /**
     * 等比压缩图片到合适的大小
     *
     * @return 成功压缩返回true
     */
    public boolean compress(String path) {
        boolean result = true;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        Bitmap oldBitmap = BitmapFactory.decodeFile(path, options);
        int targetWidth = maxWidth;
        int targetHeight = maxWidth * oldBitmap.getHeight() / oldBitmap.getWidth();
        Bitmap newBitmap = Bitmap.createScaledBitmap(oldBitmap, targetWidth, targetHeight, true);
        try {
            File imageFile = new File(path);
            FileOutputStream imageOS = new FileOutputStream(imageFile);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOS);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            oldBitmap.recycle();
            newBitmap.recycle();
            oldBitmap = null;
            newBitmap = null;
            System.gc();
            return result;
        }
    }

    @SystemService
    WindowManager windowManager;

    /**
     * 根据当前的屏幕计算图片出4:3大小的图片
     *
     * @return 返回的是一个长度为2数组，第0位数据代表计算后的宽度 第1位数据代表计算后的长度
     */
    public int[] calculateRightSize() {
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);
        int resultWidth = size.x;
        int resultHeight = size.x * 3 / 5;
        int[] result = {resultWidth, resultHeight};
        return result;
    }
}
