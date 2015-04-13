package com.qz.lifehelper.entity.json;

/**
 * 这是聚合网的第二种reponseJson
 */
public class JuheResponseJsonBean2<T> {

    private String reason;
    private Integer error_code;
    private JuheList<T> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public JuheList<T> getResult() {
        return result;
    }

    public void setResult(JuheList<T> result) {
        this.result = result;
    }
}
