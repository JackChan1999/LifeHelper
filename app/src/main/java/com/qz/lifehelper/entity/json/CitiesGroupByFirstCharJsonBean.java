package com.qz.lifehelper.entity.json;

import java.util.List;

/**
 * 该类是ChooseCity页面用到的全国城市列表的json数据对应的bean
 */
public class CitiesGroupByFirstCharJsonBean {
    // 分组标题，这里用的是首字母
    String section;

    // 首字母对应的城市的list集合
    List<CityJsonBean> cities;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<CityJsonBean> getCities() {
        return cities;
    }

    public void setCities(List<CityJsonBean> cities) {
        this.cities = cities;
    }
}
