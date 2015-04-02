package com.qz.lifehelper.entity.json;

/**
 * 这是聚合网的机场的json数据
 */
public class AirportJsonBean {

    private String city;
    private String spell;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    @Override
    public String toString() {
        return city;
    }
}
