package com.yusuf.finartz.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Result implements Serializable {

    private ResultStatus status = ResultStatus.OK;
    private String message;
    private String errorCode;


    public Result() {
    }

    public Result(ResultStatus status) {
        this.status = status;
    }

    public Result(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public Result setStatus(ResultStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public Result setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }
    @JsonIgnore
    public boolean isOk() {
        return getStatus() == ResultStatus.OK;
    }

}
