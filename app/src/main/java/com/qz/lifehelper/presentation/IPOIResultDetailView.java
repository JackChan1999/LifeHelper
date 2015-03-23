package com.qz.lifehelper.presentation;

import android.graphics.Bitmap;

/**
 * Created by kohoh on 15/3/23.
 */
public interface IPOIResultDetailView {
    public void setPOIImage(Bitmap poiImage);

    public void setTitle(String title);

    public void setTel(String tel);

    public void setAdd(String add);

    public void setDetail(String detail);
}
