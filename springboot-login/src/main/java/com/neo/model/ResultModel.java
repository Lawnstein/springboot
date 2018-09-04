package com.neo.model;


public class ResultModel {

    /**
     * 响应码
     */
    private int errcode;

    /**
     * 响应信息说明
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultModel(String message) {
        this.errcode = 999;
        this.message = message;
        this.data = "";
    }

    public ResultModel(int errcode, String message, Object data) {
        this.errcode = errcode;
        this.message = message;
        this.data = data;
    }

    public ResultModel(int errcode, String message) {
        this.errcode = errcode;
        this.message = message;
        this.data = "";
    }

    public ResultModel(ResultStatus status) {
        this.errcode = status.getErrcode();
        this.message = status.getMessage();
        this.data = "";
    }

    public ResultModel(ResultStatus status, Object data) {
        this.errcode = status.getErrcode();
        this.message = status.getMessage();
        this.data = data;
    }

    public static ResultModel ok(Object data) {
        return new ResultModel(ResultStatus.SUCCESS, data);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }


}
