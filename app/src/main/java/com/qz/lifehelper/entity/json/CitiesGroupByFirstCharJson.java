package com.qz.lifehelper.entity.json;

import java.util.List;

/**
 * Created by kohoh on 15/3/16.
 */
public class CitiesGroupByFirstCharJson {
	// 分组标题
	String section;

	List<CityJson> cities;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public List<CityJson> getCities() {
		return cities;
	}

	public void setCities(List<CityJson> cities) {
		this.cities = cities;
	}
}
