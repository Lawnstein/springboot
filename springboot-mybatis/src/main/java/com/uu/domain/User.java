package com.uu.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 年龄
     */
    private Integer ages;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}