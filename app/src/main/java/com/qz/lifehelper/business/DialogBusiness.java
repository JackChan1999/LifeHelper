package com.qz.lifehelper.business;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.qz.lifehelper.ui.fragment.ContextMenuDialogFragment;
import com.qz.lifehelper.ui.fragment.ProgressDialogFragment;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责处理相应的dialog的业务逻辑
 */
@EBean(scope = EBean.Scope.Singleton)
public class DialogBusiness {

    static public class ProgressDialogBuilder {
        public DialogFragment create() {
            return new ProgressDialogFragment();
        }
    }

    static public class ContextMenuDialogBuilder extends ContextMenuDialogFragment.Builder {

    }

    private Map<String, DialogFragment> fragmentMap = new HashMap<>();

    /**
     * 显示Dialog
     *
     * @param dialogFragment 实现dialog的fragment 可以通过对应的Builder获得
     * @param dialogTag      ＃hideDialog使用的tag
     */
    public void showDialog(FragmentManager fragmentManager, DialogFragment dialogFragment, String dialogTag) {
        if (fragmentMap.containsKey(dialogTag)) {
            fragmentMap.remove(dialogTag);
        }
        fragmentMap.put(dialogTag, dialogFragment);
        dialogFragment.show(fragmentManager, dialogTag);
    }

    /**
     * 根据对应的tag隐藏Dialog
     */
    public void hideDialog(String diaglogTag) {
        if (fragmentMap.containsKey(diaglogTag)) {
            fragmentMap.get(diaglogTag).dismiss();
            fragmentMap.remove(diaglogTag);
        }
    }
}
