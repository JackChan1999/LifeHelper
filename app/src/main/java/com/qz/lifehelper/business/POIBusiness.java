package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.entity.json.POIResultJsonBean;
import com.qz.lifehelper.service.BaiduPOIService;
import com.qz.lifehelper.service.IPOIService;
import com.qz.lifehelper.service.POIOnlineService_;
import com.qz.lifehelper.service.POIOutlineService_;
import com.qz.lifehelper.ui.activity.POIAddFragment;
import com.qz.lifehelper.ui.fragment.POIAlterFragment;
import com.qz.lifehelper.ui.fragment.POIDetailFragment;
import com.qz.lifehelper.ui.fragment.POIListFragment;
import com.qz.lifehelper.ui.fragment.PersonalPOIListFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * 该类主要负责与POI先相关的业务逻辑
 * <p/>
 * 所有加载到的POI信息都会被缓存，方便POIResultDetail的调用
 */
@EBean
public class POIBusiness {
    // 是否正在加载POI数据
    boolean isLoding = false;

    @RootContext
    Context context;

    @Bean
    BaiduPOIService baiduPOIService;

    IPOIService poiService;

    @Bean
    ImageBusiness imageBusiness;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    private int baiduPoiCurrentPagerNumber = -1;

    @Bean
    AppBusiness appBusiness;

    @AfterInject
    void setService() {
        if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.OUTLINE)) {
            poiService = POIOutlineService_.getInstance_(context);
        } else {
            poiService = POIOnlineService_.getInstance_(context);
        }
    }


    /**
     * 新增poi信息
     */
    public Task<POIResultBean> addPOIItem(final POIResultBean poiResultBean) {
        return imageBusiness.uploadImageToQiniu(poiResultBean.imageBean)
                .onSuccessTask(new Continuation<ImageBean, Task<ImageBean>>() {
                    @Override
                    public Task<ImageBean> then(Task<ImageBean> task) throws Exception {
                        return imageBusiness.uploadImageToLeancloud(task.getResult());
                    }
                }).onSuccessTask(new Continuation<ImageBean, Task<POIResultBean>>() {
                    @Override
                    public Task<POIResultBean> then(Task<ImageBean> task) throws Exception {
                        poiResultBean.imageBean = task.getResult();
                        return poiService.addPOIItem(poiResultBean);
                    }
                });
    }

    /**
     * 获取POI数据
     *
     * 先获取自产的POI数据，如果没有自产的数据则获取百度的POI数据
     *
     * @param cityBean     要查询数据的城市
     * @param categoryBean 要查询数据的分类
     * @param count        每页的个数
     * @param lastestItem  当前最后一个数据。用于分页，如果为null，则会加载第一页
     */
    public Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, POIResultBean lastestItem) {
        if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.OUTLINE)) {
            return poiService.getPOIItems(cityBean, categoryBean, count, lastestItem != null ? lastestItem.createdAt : null, null);
        }

        if (baiduPoiCurrentPagerNumber == -1) {
            return poiService.getPOIItems(
                    cityBean
                    , categoryBean
                    , count
                    , lastestItem != null ? lastestItem.createdAt : null, null)
                    .continueWithTask(new Continuation<List<POIResultBean>, Task<List<POIResultBean>>>() {
                        @Override
                        public Task<List<POIResultBean>> then(Task<List<POIResultBean>> task) throws Exception {
                            if (task.isFaulted()) {
                                baiduPoiCurrentPagerNumber++;
                                return baiduPOIService.getPoiItems(cityBean, categoryBean, count, baiduPoiCurrentPagerNumber);
                            } else {
                                List<POIResultBean> poiItemBeans = task.getResult();
                                if (poiItemBeans.size() == 0) {
                                    baiduPoiCurrentPagerNumber++;
                                    return baiduPOIService.getPoiItems(cityBean, categoryBean, count, baiduPoiCurrentPagerNumber);
                                } else {
                                    return Task.forResult(poiItemBeans);
                                }
                            }
                        }
                    }, Task.BACKGROUND_EXECUTOR);
        } else {
            baiduPoiCurrentPagerNumber++;
            return baiduPOIService.getPoiItems(cityBean, categoryBean, count, baiduPoiCurrentPagerNumber);
        }
    }

    /**
     * 解析POIResult的json数据
     */
    public Task<List<POIResultBean>> parsePOIResult(final String tenTopSpotsJson) {
        return Task.callInBackground(new Callable<List<POIResultBean>>() {
            @Override
            public List<POIResultBean> call() throws Exception {
                Gson gson = new Gson();
                List<POIResultJsonBean> poiResultJsonBeans = gson.fromJson(tenTopSpotsJson, new TypeToken<List<POIResultJsonBean>>() {
                }.getType());

                List<POIResultBean> poiResultBeans = new ArrayList<POIResultBean>();
                for (POIResultJsonBean poiResultJsonBean : poiResultJsonBeans) {
                    poiResultBeans.add(new POIResultBean()
                            .setTitle(poiResultJsonBean.getTitle())
                            .setDetail(poiResultJsonBean.getContent())
                            .setImageBean(ImageBean.generateImage(poiResultJsonBean.getImage(), ImageBean.ImageType.OUTLINE)));
                }
                return poiResultBeans;
            }
        });
    }

    /**
     * 前往POIListFragment
     */
    public void toPOIListFragment(FragmentTransaction transaction, POICategoryBean categoryBean, CityBean cityBean) {

        POIListFragment fragment = new POIListFragment.Builder()
                .setCategory(categoryBean)
                .setCity(cityBean)
                .create();

        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往POIDetailFragment
     */
    public void toPOIDetailFragment(FragmentTransaction transaction, POIResultBean poiItemBean) {
        POIDetailFragment fragment = new POIDetailFragment.Builder()
                .setPOItemBean(poiItemBean)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往POIAddFragment
     */
    public void toPOIAddFragment(FragmentTransaction transaction, POIAddFragment.Callback callback, POICategoryBean categoryBean) {
        POIAddFragment fragment = new POIAddFragment.Builder()
                .setCategory(categoryBean)
                .setCallback(callback)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往POI修改页面
     */
    public void toPOIAlterFragment(final FragmentTransaction transaction, final POIResultBean poiItemBean, final POIAlterFragment.Callback callback) {

        authenticationBusiness.getCurrentUser(false)
                .continueWith(new Continuation<UserInfoBean, Void>() {
                    @Override
                    public Void then(Task<UserInfoBean> task) throws Exception {
                        if (task.isFaulted()) {
                            Toast.makeText(context, "请先登录", Toast.LENGTH_LONG).show();
                        } else {
                            UserInfoBean userInfoBean = task.getResult();

                            if (authenticationBusiness.isBaiduUser(userInfoBean)) {
                                Toast.makeText(context, "你没有该权限", Toast.LENGTH_LONG).show();
                            } else {
                                if (userInfoBean.id.equals(poiItemBean.userInfoBean.id) || authenticationBusiness.isSuperUser(userInfoBean)) {
                                    POIAlterFragment fragment = new POIAlterFragment.Builder()
                                            .setCallback(callback)
                                            .setPOIItemBean(poiItemBean)
                                            .create();
                                    transaction.add(android.R.id.content, fragment);
                                    transaction.commit();
                                } else {
                                    Toast.makeText(context, "你没有该权限", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        return null;
                    }
                });
    }

    /**
     * 前往我发布到POI信息页面
     */
    public void toMyPOI(FragmentTransaction transaction) {
        PersonalPOIListFragment fragment = new PersonalPOIListFragment.Builder().create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
//        toP2PCategoryFragment(transaction, new P2pCategoryFragment.Callback() {
//            @Override
//            public void onCategorySelected(P2PCategoryBean categoryBean) {
//
//            }
//        });
    }

    /**
     * 修改POI信息
     */
    public Task<POIResultBean> alterPOIItem(POIResultBean poiItemBean) {
        return poiService.alterPOIItem(poiItemBean);
    }

    /**
     * 删除POI信息
     */
    public Task<POIResultBean> deletePOIItem(POIResultBean poiItemBean) {
        return poiService.deletePOIItem(poiItemBean);
    }

    /**
     * 获取个人发布的的POI信息
     *
     * @param count        每页的个数
     * @param lastestItem  用于分页。如果为null，则为第一页
     * @param userInfoBean 用户
     */
    public Task<List<POIResultBean>> getPersonalPOIItems(int count, POIResultBean lastestItem, UserInfoBean userInfoBean) {
        if (userInfoBean != null && authenticationBusiness.isSuperUser(userInfoBean)) {
            userInfoBean = null;
        }
        return poiService.getPOIItems(null, null, count, lastestItem != null ? lastestItem.createdAt : null, userInfoBean);
    }
}
