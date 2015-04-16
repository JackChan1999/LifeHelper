package com.qz.lifehelper.entity.json;

import com.google.gson.annotations.Expose;

/**
 * 分装来p2p分类目录的json数据
 */
public class P2PCategoryJsonBean {

    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private String image;

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
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
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

}