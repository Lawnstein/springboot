package com.uu.domain;

import lombok.Data;

@Data
public class User {
    /**
     * Id
     */
    private Integer id;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private String ages;
}