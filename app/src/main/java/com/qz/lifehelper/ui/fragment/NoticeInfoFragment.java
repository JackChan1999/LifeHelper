package com.qz.lifehelper.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.NoticeInfoBusiness;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 公告信息页
 */
@EFragment(R.layout.fragment_notice_info)
public class NoticeInfoFragment extends BaseFragment {

    /**
     * 处理所有的点击事件，否则会造成误按操作。
     * 这是由于使用类FragmentTranstlaction的add而非relpace导致的，暂时没有找到更好的解决方案。
     */
    @AfterViews
    void setRootView() {
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    /**
     * 显示公告信息
     */
    @ViewById(R.id.notice_info_content_tv)
    TextView noticeInfoContentTv;

    @Bean
    NoticeInfoBusiness noticeInfoBusiness;


    @Bean
    DialogBusiness dialogBusiness;

    @AfterViews
    void setNoticeInfoContentTv() {
        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder()
                .create()
                , "NoticeInfo");
        noticeInfoBusiness.getNoticeInfo().onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                dialogBusiness.hideDialog("NoticeInfo");
                noticeInfoContentTv.setText(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.title_tv)
    TextView titleTv;

    @AfterViews
    void setToolbar() {
        titleTv.setText("公告信息");
    }

}
