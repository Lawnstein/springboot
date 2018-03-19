package com.uu.husky.domain;

import lombok.Data;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 18:48
 **/
@Data
public class UserEntity {
    private Long id;
    private String userName;
    private String passWord;

    /**
     * 用于记录group查询 出的数据条数
     */
    private int total;
}
