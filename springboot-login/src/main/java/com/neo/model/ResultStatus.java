package com.neo.model;

public enum ResultStatus {

    SUCCESS(0, "成功"),

    MISSING_ARGS(1001, "参数不全"),
    NEED_PERMISSION(1002, "需要权限"),
    TARGET_IS_EXISTS(1003, "相关对象已存在"),
    DATE_TRANSFORM_ERROR(1004, "日期格式转换异常"),
    ACCOUNT_OR_PASSWORD_ERROR(1005, "帐号或密码错误"),
    USER_NOT_FOUND(1006, "用户不存在"),
    ROLE_HAS_AUTH_USER(1007, "该角色已授权给用户"),
    BAD_MOBILE(1008, "手机号不合法"),
    SMSCODE_SEND_TOO_FREQUENTLY(1009, "短信验证码发送频繁"),
    SMSCODE_NOT_FOUND(1010, "未找到该手机号验证码"),
    SMSCODE_TIMOUT(1011, "验证码超时，请重新发送"),
    DEPT_NAME_EXISTS(1012, "部门名称已存在"),
    DEPT_EXISTS_CHILDERN(1013, "该部门存在子部门"),
    DEPT_EXISTS_USER(1014, "该部门存在用户"),
    DEPT_ROOT_NOT_REMOVE(1015, "根部门不能删除"),
    DEPT_ROOT_NOT_UPDATE(1016, "根部门不能修改"),
    ARGS_ERROR(1017, "参数异常"),
    SCHOOL_NOT_FOUND(1018, "驾校不存在"),
    ANNEXE_NOT_FOUND(1019, "附件不存在"),
    USER_HAS_SCHOOL(1020, "该用户存在负责驾校不能删除"),
    USER_HAS_CUSTOMER(1021, "该用存在跟进驾校不能删除"),
    TARGET_NOT_EXISTS(1022, "相关对象已存在");

    private int errcode;

    private String message;

    ResultStatus(int errcode, String message) {
        this.errcode = errcode;
        this.message = message;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
