package com.qz.lifehelper.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.ui.fragment.BaseFragment;
import com.qz.lifehelper.ui.fragment.TakePhotoFragment;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 增加POI信息
 */

@EFragment(R.layout.fragment_poi_add)
public class POIAddFragment extends BaseFragment {

    private static final String TAG = POIAddFragment.class.getSimpleName() + "_TAG";

    static public class Builder {

        private POIAddFragment fragment = new POIAddFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public Builder setCategory(POICategoryBean category) {
            fragment.categoryBean = category;
            return this;
        }

        public POIAddFragment create() {
            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }

            if (fragment.categoryBean == null) {
                throw new IllegalStateException("没有设置分类");
            }

            return fragment;
        }
    }

    private POICategoryBean categoryBean;

    /**
     * 上传完成后会回调该接口
     */
    public interface Callback {

        /**
         * 成功上传
         *
         * @param poiItemBean 上传到服务器的poiItem
         */
        public void onAddPOIItemSuccess(POIResultBean poiItemBean);
    }

    private Callback callback;

    @ViewById(R.id.image_iv)
    ImageView imageIv;

    @ViewById(R.id.take_photo_ll)
    View takePhotoBn;

    private ImageBean imageBean;

    @Click(R.id.take_photo_ll)
    void takePhoto() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        TakePhotoFragment fragment = new TakePhotoFragment.Builder()
                .setCallback(new TakePhotoFragment.Callback() {
                    @Override
                    public void onGetPhoto(final ImageBean imageBean) {
                        POIAddFragment.this.imageBean = imageBean;
                        takePhotoBn.setVisibility(View.GONE);
                        imageIv.setVisibility(View.VISIBLE);
                        Picasso.with(POIAddFragment.this.getActivity())
                                .load(imageBean.imageSrc)
                                .into(imageIv);
                    }
                })
                .create();
        transaction.add(fragment, null);
        transaction.commit();
    }

    @ViewById(R.id.address_et)
    EditText AddEt;

    @ViewById(R.id.tel_et)
    EditText TelEt;

    @ViewById(R.id.title_et)
    EditText TitleEt;

    @ViewById(R.id.detail_et)
    EditText DetailEt;

    @Bean
    POIBusiness poiBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Bean
    LocationBusiness locationBusiness;

    @Click(R.id.submit_bn)
    void submit() {
        final String title = TitleEt.getText().toString();
        final String add = AddEt.getText().toString();
        final String tel = TelEt.getText().toString();
        final String detail = DetailEt.getText().toString();

        //TODO 检测数据是否合法

        authenticationBusiness.getCurrentUser(true)
                .continueWithTask(new Continuation<UserInfoBean, Task<Void>>() {
                    @Override
                    public Task<Void> then(Task<UserInfoBean> task) throws Exception {
                        if (task.isFaulted()) {
                            Toast.makeText(POIAddFragment.this.getActivity(), "获取当前用户失败", Toast.LENGTH_LONG).show();
                        } else {
                            POIResultBean poiItemBean = new POIResultBean()
                                    .setTitle(title)
                                    .setDetail(detail)
                                    .setAddress(add)
                                    .setTel(tel)
                                    .setImageBean(imageBean)
                                    .setPoiCategoryBean(categoryBean)
                                    .setUserInfoBean(task.getResult())
                                    .setCityBean(locationBusiness.getCurrentCity());

                            dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "upload_poi");
                            poiBusiness.addPOIItem(poiItemBean).continueWith(new Continuation<POIResultBean, Void>() {
                                @Override
                                public Void then(Task<POIResultBean> task) throws Exception {
                                    dialogBusiness.hideDialog("upload_poi");
                                    if (task.isFaulted()) {
                                        Toast.makeText(POIAddFragment.this.getActivity(), "上传失败", Toast.LENGTH_LONG).show();
                                        Log.e(TAG, "upload poi fail", task.getError());
                                    } else {
                                        Log.d(TAG, "upload poi success");
                                        callback.onAddPOIItemSuccess(task.getResult());
                                    }
                                    return null;
                                }
                            }, Task.UI_THREAD_EXECUTOR);
                        }
                        return null;
                    }
                });
    }

    @ViewById(R.id.title_tv)
    TextView titleTv;

    @AfterViews
    void setToolbar() {
        titleTv.setText("新增信息");
    }
}
