package com.rvlt.server.data.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseStatus {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
