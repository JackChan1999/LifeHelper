package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.POIResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kohoh on 15/3/23.
 */
@EActivity(R.layout.activity_poi_result_detail)
public class POIResultDetailActivity extends ActionBarActivity{


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

    static final public String POI_RESULT_ID = "POI_RESULT_ID";

    @Extra(POI_RESULT_ID)
    String poiResultId;

    @Bean
    POIBusiness poiBusiness;

    /**
     * 设置POIResultDetail的数据
     */
    @AfterViews
    void setData() {
        POIResult poiResult = poiBusiness.getPOIResult(poiResultId);
        titleIv.setText(poiResult.title);
        telTv.setText("tel:" + poiResult.tel);
        addTv.setText("add:"+poiResult.address);
        detailTv.setText(poiResult.detail);
        //TODO 设置图片
    }

    public static Intent generateIntent(Context context, String poiResultId) {
        Intent intent = new Intent(context, POIResultDetailActivity_.class);
        intent.putExtra(POI_RESULT_ID, poiResultId);
        return intent;
    }
}
