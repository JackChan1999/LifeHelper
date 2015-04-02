package com.qz.lifehelper.entity.json;

import java.util.List;

/**
 * 该类是聚合网返回的json数据
 */
public class JuheResponseJsonBean<T> {

    private Integer error_code;
    private String reaseon;
    private List<T> result;

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getReaseon() {
        return reaseon;
    }

    public void setReaseon(String reaseon) {
        this.reaseon = reaseon;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
