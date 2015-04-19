package com.qz.lifehelper.persist;

import android.content.Context;

import com.google.gson.Gson;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.json.P2PItemJsonBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 持续化p2p相关的数据
 */
@EBean
public class P2PPersist {

    @RootContext
    Context context;

    /**
     * 获取p2p分类目录信息
     */
    public String getP2PCategory() throws IOException {
        InputStream p2pCategoryIS = context.getAssets().open("p2p_category");
        return IOUtils.toString(p2pCategoryIS);
    }

    private static final String P2P = "P2P";

    static Map<String, String> p2pFileMap = new HashMap<>();

    static {
        p2pFileMap.put("电子数码", "electric");
    }

    /**
     * 根据分类目录获取相应的P2P数据
     * <p/>
     * 这只有在使用虚拟服务器时才会被使用
     */
    public String getP2PList(P2PCategoryBean categoryBean) throws IOException {
        File p2pFile = getP2PFile(categoryBean);
        FileInputStream p2pFileIS = new FileInputStream(p2pFile);
        return IOUtils.toString(p2pFileIS);
    }

    /**
     * 获取对应分类的p2p数据
     * <p/>
     * 该方法只有在使用虚拟服务器时才会被使用
     */
    public File getP2PFile(P2PCategoryBean categoryBean) throws IOException {
        File p2pFile = new File(context.getDir(P2P, Context.MODE_PRIVATE), categoryBean.title);
        if (!p2pFile.exists()) {
            p2pFile.createNewFile();
            try {
                //如果存在默认的数据，则添加默认的数据
                InputStream defaultJsonIS = context.getAssets()
                        .open("p2p" + File.separator + p2pFileMap.get(categoryBean.title));
                String defaultJson = IOUtils.toString(defaultJsonIS);
                FileOutputStream p2pFileOS = new FileOutputStream(p2pFile);
                IOUtils.write(defaultJson, p2pFileOS);
            } catch (Exception e) {

            }
        }
        return p2pFile;
    }

    /**
     * 设置p2p文件
     * <p/>
     * 该方法只有在使用虚拟服务器时才会被调用
     */
    public void setP2PFile(List<P2PItemJsonBean> data, P2PCategoryBean categoryBean) throws IOException {
        Gson gson = new Gson();
        String p2pJson = gson.toJson(data);
        setP2PFile(p2pJson, categoryBean);
    }

    /**
     * 设置p2p文件
     * <p/>
     * 该方法只有在使用虚拟服务器时才会被调用
     */
    public void setP2PFile(String data, P2PCategoryBean categoryBean) throws IOException {
        File p2pFile = getP2PFile(categoryBean);
        p2pFile.deleteOnExit();
        p2pFile.createNewFile();
        FileOutputStream p2pFileOS = new FileOutputStream(p2pFile);
        IOUtils.write(data, p2pFileOS);
    }
}
