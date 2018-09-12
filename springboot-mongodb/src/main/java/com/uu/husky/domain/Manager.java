package com.uu.husky.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-14 上午9:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "manager")
public class Manager {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 日期
     */
    private Date date;

    /**
     * 分数s
     */
    private Integer score;

}
