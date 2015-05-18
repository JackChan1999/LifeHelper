package com.qz.lifehelper.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qz.lifehelper.dao.DaoMaster;
import com.qz.lifehelper.dao.DaoSession;
import com.qz.lifehelper.dao.POI;
import com.qz.lifehelper.dao.POIDao;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * POI的离线服务器
 */
@EBean
public class POIOutlineService implements IPOIService {
    private POIDao poiDao;

    @RootContext
    Context context;

    @AfterInject
    void setDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, OutlineServiceConstant.BD_NAME, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        poiDao = daoSession.getPOIDao();
    }

    /**
     * 获取POI数据
     *
     * @param cityBean     获取数据的城市。如果为null，则获取全部数据
     * @param categoryBean 获取数据的分类。如果为null，则获取全部数据
     * @param count        获取数据每页的个数
     * @param after        最后一个数据的创建时间。用于分页，如果为null，则加载第一页
     * @param userInfoBean 获取数据属于的用户。如果为null，则获取全部数据
     */
    public Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, final Date after, final UserInfoBean userInfoBean) {

        return Task.callInBackground(new Callable<List<POIResultBean>>() {
            @Override
            public List<POIResultBean> call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();
                QueryBuilder<POI> queryBuilder = poiDao.queryBuilder();
                if (cityBean != null) {
                    queryBuilder.where(POIDao.Properties.City.eq(cityBean.cityName));
                }
                if (categoryBean != null) {
                    queryBuilder.where(POIDao.Properties.Category.eq(categoryBean.categotyName));
                }
                if (userInfoBean != null) {
                    queryBuilder.where(POIDao.Properties.UserId.eq(userInfoBean.id));
                }
                queryBuilder.limit(count);
                if (after != null) {
                    queryBuilder.where(POIDao.Properties.CreatedAt.lt(after));
                }
                queryBuilder.orderDesc(POIDao.Properties.CreatedAt);
                List<POI> pois = queryBuilder.list();
                List<POIResultBean> poiItemBeans = new ArrayList<POIResultBean>();
                for (POI poi : pois) {
                    POIResultBean poiItemBean = convertToPOIItemBean(poi);
                    poiItemBeans.add(poiItemBean);
                }
                return poiItemBeans;
            }
        });
    }

    /**
     * 添加POI信息
     */
    public Task<POIResultBean> addPOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();
                POI poi = convertToPOI(poiItemBean);
                poi.setCreatedAt(new Date());
                Long id = poiDao.insert(poi);
                poiItemBean.setId(String.valueOf(id));
                return poiItemBean;
            }
        });
    }

    /**
     * 修改POI信息
     */
    public Task<POIResultBean> alterPOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();
                POI poi = convertToPOI(poiItemBean);
                poiDao.update(poi);
                return poiItemBean;
            }
        });
    }

    /**
     * 删除POI信息
     */
    public Task<POIResultBean> deletePOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();
                POI poi = convertToPOI(poiItemBean);
                poiDao.delete(poi);
                return poiItemBean;
            }
        });
    }


    /**
     * 将POIItemBean转换为POI
     */
    private POI convertToPOI(POIResultBean poiItemBean) {
        POI poi = new POI();
        poi.setTitle(poiItemBean.title);
        poi.setDetail(poiItemBean.detail);
        poi.setTel(poiItemBean.tel);
        poi.setAdd(poiItemBean.address);
        poi.setCategory(poiItemBean.poiCategoryBean.categotyName);
        poi.setImageId(poiItemBean.imageBean.id != null ? Long.valueOf(poiItemBean.imageBean.id) : null);
        poi.setUserId(poiItemBean.userInfoBean.id != null ? Long.valueOf(poiItemBean.userInfoBean.id) : null);
        poi.setCity(poiItemBean.cityBean.cityName);
        poi.setCreatedAt(poiItemBean.createdAt);
        poi.setId(poiItemBean.id != null ? Long.valueOf(poiItemBean.id) : null);
        return poi;
    }

    /**
     * 将POIObjcet转换为POIItemBean
     */
    private POIResultBean convertToPOIItemBean(POI poi) {
        POIResultBean poiResultBean = new POIResultBean();
        poiResultBean.setTitle(poi.getTitle())
                .setDetail(poi.getDetail())
                .setAddress(poi.getAdd())
                .setTel(poi.getTel())
                .setPoiCategoryBean(POICategoryBean.generate(poi.getCategory()))
                .setCityBean(CityBean.generateCity(poi.getCity()))
                .setId(poi.getId() != null ? String.valueOf(poi.getId()) : null)
                .setUserInfoBean(UserInfoBean.generateBean(poi.getUser().getName(), null, String.valueOf(poi.getUserId())))
                .setImageBean(ImageBean.generateImage(poi.getImage().getImageSrc(), ImageBean.ImageType.OUTLINE, String.valueOf(poi.getImageId())))
                .setCreatedAt(poi.getCreatedAt());

        return poiResultBean;
    }
}
