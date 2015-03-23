package com.qz.lifehelper.presentation;

import android.graphics.Bitmap;

/**
 * Created by kohoh on 15/3/23.
 */
public interface IPersonalView {

    void login(Bitmap userIcon, Bitmap userIconBag);

    void logout(Bitmap userIcon, Bitmap userIconBag);

    public void setAppVersion(String version);
}
