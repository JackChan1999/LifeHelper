package com.qz.lifehelper.entity;

/**
 * ChooseCity列表Item数据：分组标题
 */
public class ChooseCityListItemSection implements ChooseCityListItemData {
	public String title;

	static public ChooseCityListItemSection generateSection(String title) {
		ChooseCityListItemSection chooseCityListItemSection = new ChooseCityListItemSection();
		chooseCityListItemSection.title = title;
		return chooseCityListItemSection;
	}

	@Override
	public ChooseCityListItemType getItemType() {
		return ChooseCityListItemType.SECTION;
	}
}
