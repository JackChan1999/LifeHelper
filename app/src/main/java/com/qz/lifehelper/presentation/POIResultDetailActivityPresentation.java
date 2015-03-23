package com.qz.lifehelper.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.POIResult;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by kohoh on 15/3/23.
 */
@EBean
public class POIResultDetailActivityPresentation {

    private String poiResultId;

    @Bean
    public POIBusiness poiBusiness;

    private IPOIResultDetailView poiResultDetailView;

    private POIResult poiResult;

    public void bind(IPOIResultDetailView poiResultDetailView) {
        this.poiResultDetailView = poiResultDetailView;
        this.poiResult = poiBusiness.getPOIResult(poiResultId);
        if (poiResult != null) {
            poiResultDetailView.setPOIImage(getPOIImage(poiResult.poiIv));
            poiResultDetailView.setTitle(getTitle(poiResult.title));
            poiResultDetailView.setTel(getTel(poiResult.tel));
            poiResultDetailView.setAdd(getAdd(poiResult.address));
            poiResultDetailView.setDetail(getDetail(poiResult.detail));
        }
    }

    public void unbind() {

    }

    public void setPoiResultId(String poiResultId) {
        this.poiResultId = poiResultId;
    }

    private Bitmap getPOIImage(String imageUrl) {
        return null;
    }

    private String getTitle(String rawTitle) {
        return rawTitle;
    }

    private String getTel(String rawTel) {
        return "tel:" + rawTel;
    }

    private String getAdd(String rawAdd) {
        return "add:" + rawAdd;
    }

    private String getDetail(String rawDetail) {
        return rawDetail;
    }
}
