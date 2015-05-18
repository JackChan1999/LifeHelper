package com.qz.lifehelper.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qz.lifehelper.dao.DaoMaster;
import com.qz.lifehelper.dao.DaoSession;
import com.qz.lifehelper.dao.User;
import com.qz.lifehelper.dao.UserDao;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;
import java.util.concurrent.Callable;

import bolts.Task;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 用于进行身份验证的Service
 * <p/>
 * 这是一个假实现，一个虚拟服务器
 */
@EBean
public class AuthenticateOutlineService implements IAuthenticateService {

    @RootContext
    Context context;

    private UserDao userDao;

    @AfterInject
    void setDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, OutlineServiceConstant.BD_NAME, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    /**
     * 登录
     */
    public Task<UserInfoBean> login(final String userName, final String password) {
        return Task.callInBackground(new Callable<UserInfoBean>() {
            @Override
            public UserInfoBean call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();

                QueryBuilder<User> queryBuilder = userDao.queryBuilder();
                User user = queryBuilder.where(UserDao.Properties.Name.eq(userName))
                        .where(UserDao.Properties.Password.eq(password))
                        .unique();
                UserInfoBean userInfoBean = UserInfoBean.generateBean(
                        user.getName()
                        , getDefaultUserIcon()
                        , String.valueOf(user.getId()));
                if (user == null) {
                    throw new Exception("用户名或密码不正确");
                } else {
                    return userInfoBean;
                }
            }
        });
    }


    /**
     * 注册
     */
    public Task<UserInfoBean> signin(final String userName, final String password) {
        return Task.callInBackground(new Callable<UserInfoBean>() {
            @Override
            public UserInfoBean call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();

                User user = new User(null, new Date(), userName, password);
                Long id = userDao.insert(user);
                return UserInfoBean.generateBean(userName, getDefaultUserIcon(), String.valueOf(id));
            }
        });
    }

    /**
     * 获取默认当头像
     */
    private ImageBean getDefaultUserIcon() {
        return ImageBean.generateImage("file:///android_asset/user_icon_1.png", ImageBean.ImageType.OUTLINE);
    }
}
