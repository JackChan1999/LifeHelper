package com.qz.lifehelper.ui.fragment;

import android.widget.EditText;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PItemBean;

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

    static public class Builder {

        private P2PAddFragment fragment = new P2PAddFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public P2PAddFragment create() {
            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }
            return fragment;
        }
    }

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

    @Click(R.id.take_photo_ll)
    void takePhoto() {
        //TODO 实现拍摄照片
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

    @Click(R.id.submit_bn)
    void submit() {
        String title = TitleEt.getText().toString();
        String add = AddEt.getText().toString();
        String tel = TelEt.getText().toString();
        String price = PriceEt.getText().toString();
        String detail = DetailEt.getText().toString();
        //TODO 获取照片

        //TODO 检测数据是否合法

        P2PItemBean p2pItemBean = new P2PItemBean()
                .setTitle(title)
                .setDetail(detail)
                .setAddress(add)
                .setTitle(tel)
                .setPrice(price);

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "upload_p2p");
        p2pBusiness.uploadP2PItem(p2pItemBean).onSuccess(new Continuation<P2PItemBean, Void>() {
            @Override
            public Void then(Task<P2PItemBean> task) throws Exception {
                dialogBusiness.hideDialog("upload_p2p");
                callback.onAddP2PItemSuccess(task.getResult());
                getFragmentManager().popBackStack();
                return null;
            }
        });
    }
}
