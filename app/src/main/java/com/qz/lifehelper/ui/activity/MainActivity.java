package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.EActivity;
import org.robobinding.binder.Binders;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.MainActivityPresentation;

/**
 * Created by kohoh on 15/3/14.
 */
@EActivity
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityPresentation presentation = new MainActivityPresentation(this);
        View rootView = Binders.inflateAndBind(this, R.layout.activity_main, presentation);
        setContentView(rootView);
    }
}
