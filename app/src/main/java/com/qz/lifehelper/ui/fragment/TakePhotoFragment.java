package com.qz.lifehelper.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.persist.ImagePersist;
import com.qz.lifehelper.utils.ImageUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.io.File;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * 获取照片页
 */
@EFragment
public class TakePhotoFragment extends BaseFragment {

    private static final int CAPTRUE_IMAGE_ACTIVITY_REQUEST_CODE = 0;

    static public class Builder {
        private TakePhotoFragment fragment = new TakePhotoFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public TakePhotoFragment create() {
            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }

            return fragment;
        }
    }

    /**
     * 当成功获取图片时会回调该接口
     */
    public interface Callback {
        /**
         * 当成功获取图片时会回调该方法
         */
        public void onGetPhoto(ImageBean imageBean);
    }

    private Callback callback;

    private Uri imageUri;

    @Bean
    ImagePersist imagePersist;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imge = new File(imagePersist.getImageDir(), generateImageName());
        imageUri = Uri.fromFile(imge);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTRUE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * 生成Image的文件名
     */
    private String generateImageName() {
        return String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    @Bean
    ImageUtil imageUtil;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTRUE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (Activity.RESULT_OK == resultCode) {
                Task.callInBackground(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        //首先压缩图片
                        return imageUtil.compress(imageUri.getPath());
                    }
                }).onSuccess(new Continuation<Boolean, Void>() {
                    @Override
                    public Void then(Task<Boolean> task) throws Exception {
                        ImageBean imageBean = ImageBean.generateImage(imageUri.toString(), ImageBean.ImageType.OUTLINE);
                        callback.onGetPhoto(imageBean);
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        }
    }
}
