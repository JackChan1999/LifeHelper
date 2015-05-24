package com.qz.lifehelper.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.P2PItemBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * P2P内容详情页
 */
@EFragment(R.layout.fragment_p2p_detail)
public class P2PDetailFragment extends BaseFragment {

    private P2PItemBean p2pItemBean;

    static public class Builder {
        private P2PDetailFragment fragment = new P2PDetailFragment_.FragmentBuilder_().build();

        public P2PDetailFragment create() {
            if (fragment.p2pItemBean == null) {
                throw new IllegalStateException("没有设置p2pItemBean");
            }
            return fragment;
        }

        public Builder setP2PItemBean(P2PItemBean p2pItemBean) {
            fragment.p2pItemBean = p2pItemBean;
            return this;
        }
    }

    @ViewById(R.id.p2p_image_iv)
    ImageView p2pImageIv;

    @ViewById(R.id.p2p_title_tv)
    TextView p2pTitleTv;

    @ViewById(R.id.p2p_address_tv)
    TextView p2pAddTv;

    @ViewById(R.id.p2p_tel_tv)
    TextView p2pTelTv;

    @ViewById(R.id.p2p_price_tv)
    TextView p2pPriceTv;

    @ViewById(R.id.p2p_content_tv)
    TextView p2pContentTv;

    @AfterViews
    void setData() {
        Picasso.with(this.getActivity())
                .load(p2pItemBean.imageBean.imageSrc)
                .into(p2pImageIv);
        p2pTitleTv.setText(p2pItemBean.title);
        p2pAddTv.setText("add:" + p2pItemBean.address);
        p2pTelTv.setText("tel:" + p2pItemBean.tel);
        p2pPriceTv.setText(p2pItemBean.price + "元");
        p2pContentTv.setText(p2pItemBean.detail);
    }
}
