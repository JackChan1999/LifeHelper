package com.qz.lifehelper.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qz.lifehelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个用于替代ContextMenu当Dialge
 */
public class ContextMenuDialogFragment extends DialogFragment {

    public static class Item {

        private Callback callback;
        private String title;

        public static class Builder {
            private Item item = new Item();

            public Builder setCallback(Callback callback) {
                item.callback = callback;
                return this;
            }

            public Builder setTitle(String title) {
                item.title = title;
                return this;
            }

            public Item create() {
                if (item.title == null) {
                    throw new IllegalStateException("没有设置Title");
                }

                if (item.callback == null) {
                    throw new IllegalStateException("没有设置Callback");
                }

                return item;
            }
        }

        /**
         * Item的回调接口
         */
        public interface Callback {
            /**
             * 当Item被点击时会出发该方法
             */
            public void onClick();
        }
    }

    private List<Item> items = new ArrayList<>();

    public static class Builder {

        private ContextMenuDialogFragment fragment = new ContextMenuDialogFragment();

        public Builder addItem(String title, Item.Callback callback) {
            fragment.items.add(new Item.Builder()
                    .setTitle(title)
                    .setCallback(callback)
                    .create());
            return this;
        }

        public ContextMenuDialogFragment create() {

            if (fragment.items.size() == 0) {
                throw new IllegalStateException("没有一个Item");
            }

            return fragment;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.dialog_context_menu, null, false);
        for (final Item item : items) {
            View childView = inflater.inflate(R.layout.item_dialog_context_meny, null, false);
            ((TextView) childView.findViewById(R.id.text)).setText(item.title);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.callback.onClick();
                }
            });
            rootView.addView(childView);
        }
        return new AlertDialog.Builder(this.getActivity()).setView(rootView).create();
    }
}
