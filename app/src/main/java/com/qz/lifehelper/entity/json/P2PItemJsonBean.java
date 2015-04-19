package com.qz.lifehelper.entity.json;

import com.google.gson.annotations.Expose;

/**
 * 封装类P2P items的json数据
 */
public class P2PItemJsonBean {

    @Expose
    private String title;
    @Expose
    private String price;
    @Expose
    private String detail;
    @Expose
    private String address;
    @Expose
    private String tel;
    @Expose
    private String image;
    @Expose
    private String id;
    @Expose
    private String category;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel The tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}