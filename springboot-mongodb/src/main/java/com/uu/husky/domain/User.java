package com.uu.husky.domain;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-22 上午10:22
 **/
@Document(collection = "user")
@Data
public class User implements Serializable {
    @Id
    private ObjectId id;
    /**
     *
     */
    private String firstName;
    /**
     *
     */
    private String lastName;
    /**
     *
     */
    private Integer age;

    /**
     *
     */
    private ObjectId schoolId;

    /**
     * 省份
     */
    private String province;

    /**
     * 姓名
     */
    private String name;

    private List<Subject> subjects;

}
