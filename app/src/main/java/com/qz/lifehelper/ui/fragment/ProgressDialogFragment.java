package com.qz.lifehelper.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.qz.lifehelper.R;

/**
 * 进度加载Dialog
 */
public class ProgressDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
        return new AlertDialog.Builder(getActivity()).setView(dialogView).create();
    }
}
