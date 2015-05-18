package com.qz.lifehelper.service;

import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qz.lifehelper.entity.ImageBean;

import org.androidannotations.annotations.EBean;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 图片服务器
 */
@EBean
public class ImageOnlineService implements IImageService {

    static private final String TAG = ImageOnlineService.class.getSimpleName() + "_TAG";

    /**
     * 将图片上传到七牛云服务器
     *
     * @param imageBean 里面应该包含图片本地的地址
     * @return 返回的应该包含图片的外链地址
     */
    public Task<ImageBean> uploadImageToQiniu(final ImageBean imageBean) {

        final Task<ImageBean>.TaskCompletionSource taskCompletionSource = Task.create();

        Auth auth = Auth.create(QiniuyunConstant.ACCESS_KEY, QiniuyunConstant.SECRET_KEY);
        String token = auth.uploadToken(QiniuyunConstant.BUCKET);
        UploadManager uploadManager = new UploadManager();
        final String imageName = generateImageName();
        String imageFile = imageBean.imageSrc.replace("file://", "");
        uploadManager.put(
                imageFile
                , imageName
                , token
                , new UpCompletionHandler() {
                    @Override
                    public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                        if (responseInfo.isOK()) {
                            taskCompletionSource.setResult(ImageBean.generateImage(
                                    QiniuyunConstant.DOMAIN + "/" + imageName
                                    , ImageBean.ImageType.QINIUYUN));
                            Log.d(TAG, "upload image to qiniu success");
                        } else {
                            Exception exception = new Exception(responseInfo.error);
                            Log.e(TAG, "upload image to qiniu faile", exception);
                            taskCompletionSource.setError(new Exception(responseInfo.error));
                        }
                    }
                }
                , null);

        return taskCompletionSource.getTask();
    }

    /**
     * 将图片地址上传到leancloed
     *
     * @param imageBean 里面应该包含来图片的外链地址
     * @return 返回的应该包含leancloud的objectId
     */
    public Task<ImageBean> uploadImageToLeancloud(final ImageBean imageBean) {
        return Task.callInBackground(new Callable<ImageBean>() {
            @Override
            public ImageBean call() throws Exception {
                AVObject imageObject = convertToImageObject(imageBean);
                imageObject.save();
                return convertToImageBean(imageObject);
            }
        });
    }

    private ImageBean convertToImageBean(AVObject imageObject) {
        String src = imageObject.getString(LeancloudConstant.IMAGE_COLUME);
        String id = imageObject.getObjectId();
        return ImageBean.generateImage(src, ImageBean.ImageType.QINIUYUN, id);
    }

    private AVObject convertToImageObject(ImageBean imageBean) {
        AVObject imageObject = new AVObject(LeancloudConstant.IMAGE_TABLE);
        imageObject.put(LeancloudConstant.IMAGE_COLUME, imageBean.imageSrc);
        imageObject.setObjectId(imageBean.id);
        return imageObject;
    }

    private String generateImageName() {
        return String.valueOf(new Date().getTime());
    }

}
