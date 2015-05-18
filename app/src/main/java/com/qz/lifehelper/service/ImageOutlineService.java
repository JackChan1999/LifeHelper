package com.qz.lifehelper.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qz.lifehelper.dao.DaoMaster;
import com.qz.lifehelper.dao.DaoSession;
import com.qz.lifehelper.dao.Image;
import com.qz.lifehelper.dao.ImageDao;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 图片离线服务器
 */
@EBean
public class ImageOutlineService implements IImageService {

    @RootContext
    Context context;

    private ImageDao imageDao;

    @AfterInject
    void setDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, OutlineServiceConstant.BD_NAME, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        imageDao = daoSession.getImageDao();
    }

    @Override
    public Task<ImageBean> uploadImageToQiniu(final ImageBean imageBean) {
        return Task.callInBackground(new Callable<ImageBean>() {
            @Override
            public ImageBean call() throws Exception {
                OutlineServiceUtil.analogLoding();
                return ImageBean.generateImage(imageBean.imageSrc, ImageBean.ImageType.OUTLINE);
            }
        });
    }

    @Override
    public Task<ImageBean> uploadImageToLeancloud(final ImageBean imageBean) {
        return Task.callInBackground(new Callable<ImageBean>() {
            @Override
            public ImageBean call() throws Exception {
                OutlineServiceUtil.analogLoding();

                Image image = new Image();
                image.setCreatedAt(new Date());
                image.setImageSrc(imageBean.imageSrc);
                Long id = imageDao.insert(image);

                return ImageBean.generateImage(
                        imageBean.imageSrc
                        , ImageBean.ImageType.OUTLINE
                        , id != null ? String.valueOf(id) : null);
            }
        });
    }
}
