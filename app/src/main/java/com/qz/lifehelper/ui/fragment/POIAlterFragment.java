package com.qz.lifehelper.ui.fragment;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.POIResultBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * POI信息修改
 */
@EFragment(R.layout.fragment_poi_alter)
public class POIAlterFragment extends BaseFragment {

    /**
     * 当完成操作后会回调该接口
     */
    public interface Callback {
        /**
         * 当修改成功后会回调该接口
         */
        public void onAlterSuccess(POIResultBean poiItemBean);

        /**
         * 当删除成功后会回调该接口
         */
        public void onDeleteSuccess(POIResultBean poiItemBean);
    }

    static public class Builder {
        private POIAlterFragment fragment = new POIAlterFragment_.FragmentBuilder_().build();

        public Builder setPOIItemBean(POIResultBean poiItemBean) {
            fragment.poiItemBean = poiItemBean;
            return this;
        }

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public POIAlterFragment create() {
            if (fragment.poiItemBean == null) {
                throw new IllegalStateException("没有设置poiItemBean");
            }

            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }

            return fragment;
        }
    }

    private POIResultBean poiItemBean;

    private Callback callback;

    @ViewById(R.id.image_iv)
    ImageView imageIv;

    @ViewById(R.id.title_et)
    EditText titleEt;

    @ViewById(R.id.detail_et)
    EditText detailEt;

    @ViewById(R.id.address_et)
    EditText addEt;

    @ViewById(R.id.tel_et)
    EditText telEt;

    @AfterViews
    void setData() {
        Picasso.with(this.getActivity())
                .load(poiItemBean.imageBean.imageSrc)
                .into(imageIv);
        titleEt.setText(poiItemBean.title);
        detailEt.setText(poiItemBean.detail);
        addEt.setText(poiItemBean.address);
        telEt.setText(poiItemBean.tel);
    }

    @Bean
    POIBusiness poiBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @Click(R.id.image_iv)
    void changeImage() {
        //重新设置图片
    }

    @Click(R.id.alter_bn)
    void alter() {
        POIResultBean poiItemBean = new POIResultBean()
                .setTitle(titleEt.getText().toString())
                .setDetail(detailEt.getText().toString())
                .setAddress(addEt.getText().toString())
                .setTel(telEt.getText().toString())
                .setId(this.poiItemBean.id)
                .setImageBean(this.poiItemBean.imageBean)
                .setPoiCategoryBean(this.poiItemBean.poiCategoryBean)
                .setUserInfoBean(this.poiItemBean.userInfoBean)
                .setCityBean(this.poiItemBean.cityBean)
                .setCreatedAt(this.poiItemBean.createdAt)
                .setType(this.poiItemBean.type);

        dialogBusiness.showDialog(getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "poi_alter");
        poiBusiness.alterPOIItem(poiItemBean).onSuccess(new Continuation<POIResultBean, Void>() {
            @Override
            public Void then(Task<POIResultBean> task) throws Exception {
                dialogBusiness.hideDialog("poi_alter");
                callback.onAlterSuccess(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Click(R.id.delete_bn)
    void delete() {
        dialogBusiness.showDialog(getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "poi_delete");
        poiBusiness.deletePOIItem(poiItemBean).onSuccess(new Continuation<POIResultBean, Void>() {
            @Override
            public Void then(Task<POIResultBean> task) throws Exception {
                dialogBusiness.hideDialog("poi_delete");
                callback.onDeleteSuccess(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.title_tv)
    TextView titleTv;

    @AfterViews
    void setToolbar() {
        titleTv.setText("修改信息");
    }

}
