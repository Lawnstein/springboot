package com.uu.husky.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-07 上午11:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {
    private String name;
    private Integer score;
}
