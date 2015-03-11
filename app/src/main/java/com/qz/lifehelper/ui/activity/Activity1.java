package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.Activity1Presentation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.robobinding.binder.Binders;

//这是一个测试Activity，没有任何作用
@EActivity
public class Activity1 extends ActionBarActivity {

    //使用AndroidAnnotation注入TextView
    @ViewById
    TextView bindByAndroidAnnotationTv;

    //在完成View的注入后，绑定数据
    @AfterViews
    void setBindByAndroidAnnotationTv() {
        bindByAndroidAnnotationTv.setText("使用AndoidAnnotation绑定数据");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //使例化presentation
        Activity1Presentation presentation = new Activity1Presentation();
        //使用presentation绑定数据
        View rootView = Binders.inflateAndBind(this, R.layout.activity1, presentation);
        //设置
        setContentView(rootView);
    }
}
