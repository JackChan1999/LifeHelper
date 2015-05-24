package com.qz.lifehelper.ui.fragment;

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
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 新增P2P信息
 */
@EFragment(R.layout.fragment_p2p_add)
public class P2PAddFragment extends BaseFragment {

    private static final String TAG = P2PAddFragment.class.getSimpleName() + "_TAG";

    static public class Builder {

        private P2PAddFragment fragment = new P2PAddFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public Builder setCategory(P2PCategoryBean category) {
            fragment.categoryBean = category;
            return this;
        }

        public P2PAddFragment create() {
            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }

            if (fragment.categoryBean == null) {
                throw new IllegalStateException("没有设置分类");
            }

            return fragment;
        }
    }

    private P2PCategoryBean categoryBean;

    /**
     * 上传完成后会回调该接口
     */
    public interface Callback {

        /**
         * 成功上传
         *
         * @param p2pItemBean 上传到服务器到p2pItem
         */
        public void onAddP2PItemSuccess(P2PItemBean p2pItemBean);
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
                        P2PAddFragment.this.imageBean = imageBean;
                        takePhotoBn.setVisibility(View.GONE);
                        imageIv.setVisibility(View.VISIBLE);
                        Picasso.with(P2PAddFragment.this.getActivity())
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

    @ViewById(R.id.price_et)
    EditText PriceEt;

    @ViewById(R.id.title_et)
    EditText TitleEt;

    @ViewById(R.id.detail_et)
    EditText DetailEt;

    @Bean
    P2PBusiness p2pBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Click(R.id.submit_bn)
    void submit() {
        final String title = TitleEt.getText().toString();
        final String add = AddEt.getText().toString();
        final String tel = TelEt.getText().toString();
        final String price = PriceEt.getText().toString();
        final String detail = DetailEt.getText().toString();

        //TODO 检测数据是否合法

        authenticationBusiness.getCurrentUser(true)
                .continueWithTask(new Continuation<UserInfoBean, Task<Void>>() {
                    @Override
                    public Task<Void> then(Task<UserInfoBean> task) throws Exception {
                        if (task.isFaulted()) {
                            Toast.makeText(P2PAddFragment.this.getActivity(), "获取当前用户失败", Toast.LENGTH_LONG).show();
                        } else {
                            P2PItemBean p2pItemBean = new P2PItemBean()
                                    .setTitle(title)
                                    .setDetail(detail)
                                    .setAddress(add)
                                    .setTel(tel)
                                    .setPrice(price)
                                    .setImageBean(imageBean)
                                    .setCategoryBean(categoryBean)
                                    .setUserInfoBean(task.getResult());
                            dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "upload_p2p");
                            p2pBusiness.addP2PItem(p2pItemBean).continueWith(new Continuation<P2PItemBean, Void>() {
                                @Override
                                public Void then(Task<P2PItemBean> task) throws Exception {
                                    dialogBusiness.hideDialog("upload_p2p");
                                    if (task.isFaulted()) {
                                        Toast.makeText(P2PAddFragment.this.getActivity(), "上传失败", Toast.LENGTH_LONG).show();
                                        Log.e(TAG, "upload p2p fail", task.getError());
                                    } else {
                                        Log.d(TAG, "upload p2p success");
                                        callback.onAddP2PItemSuccess(task.getResult());
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
        titleTv.setText("新增商品");
    }
}
