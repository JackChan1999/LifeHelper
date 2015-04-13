package com.qz.lifehelper.entity.json;

import java.util.List;

/**
 * 聚合网返回的json list
 */
public class JuheList<T> {

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
