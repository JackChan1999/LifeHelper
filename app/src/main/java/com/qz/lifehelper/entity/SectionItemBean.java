package com.qz.lifehelper.entity;

/**
 * 这是ChooseCity页面中，item为分类标题时对对应的数据类型
 */
public class SectionItemBean implements ChooseCityListItemData {
    public String title;

    static public SectionItemBean generateSection(String title) {
        SectionItemBean sectionItemBean = new SectionItemBean();
        sectionItemBean.title = title;
        return sectionItemBean;
    }

    @Override
    public ChooseCityListItemType getItemType() {
        return ChooseCityListItemType.SECTION;
    }
}
