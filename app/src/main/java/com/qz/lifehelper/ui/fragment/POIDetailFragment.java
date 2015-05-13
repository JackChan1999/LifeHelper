package com.qz.lifehelper.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.POIResultBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * POI详情页
 */
@EFragment(R.layout.fragment_poi_detail)
public class POIDetailFragment extends BaseFragment {

    static public class Builder {
        private POIDetailFragment fragment = new POIDetailFragment_.FragmentBuilder_().build();

        public Builder setPOItemBean(POIResultBean poItemBean) {
            fragment.poiItemBean = poItemBean;
            return this;
        }

        public POIDetailFragment create() {

            if (fragment.poiItemBean == null) {
                throw new IllegalStateException("请设置POIItem");
            }

            return fragment;
        }
    }

    private POIResultBean poiItemBean;

    @ViewById(R.id.poi_iv)
    ImageView poiIv;

    @ViewById(R.id.title_tv)
    TextView titleIv;

    @ViewById(R.id.tel_tv)
    TextView telTv;

    @ViewById(R.id.address_tv)
    TextView addTv;

    @ViewById(R.id.detail_tv)
    TextView detailTv;

    /**
     * 设置POIResultDetail的数据
     */
    @AfterViews
    void setData() {
        titleIv.setText(poiItemBean.title);
        telTv.setText("tel:" + poiItemBean.tel);
        addTv.setText("add:" + poiItemBean.address);
        detailTv.setText(poiItemBean.detail);
        Picasso.with(this.getActivity())
                .load(poiItemBean.imageBean.imageSrc)
                .into(poiIv);
    }
}
