package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.IPOIResultDetailView;
import com.qz.lifehelper.presentation.POIResultDetailActivityPresentation;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kohoh on 15/3/23.
 */
@EActivity(R.layout.activity_poi_result_detail)
public class POIResultDetailActivity extends ActionBarActivity implements IPOIResultDetailView{


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
    public POIResultDetailActivityPresentation presentation;

    @AfterExtras
    public void setPoiResultId() {
        presentation.setPoiResultId(poiResultId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentation.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presentation.unbind();
    }

    @Override
    public void setPOIImage(Bitmap poiImage) {
        //TODO 暂时不实现
    }

    @Override
    public void setTitle(String title) {
        titleIv.setText(title);
    }

    @Override
    public void setTel(String tel) {
        telTv.setText(tel);
    }

    @Override
    public void setAdd(String add) {
        addTv.setText(add);
    }

    @Override
    public void setDetail(String detail) {
        detailTv.setText(detail);
    }

    public static Intent generateIntent(Context context, String poiResultId) {
        Intent intent = new Intent(context, POIResultDetailActivity_.class);
        intent.putExtra(POI_RESULT_ID, poiResultId);
        return intent;
    }
}
