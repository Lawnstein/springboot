package com.uu.husky.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-06 下午3:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "school")
public class School {
    private ObjectId id;
    /**
     *
     */
    private String name;
    private String description;

}
