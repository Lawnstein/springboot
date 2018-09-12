package com.uu.husky.domain.extend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-06 下午10:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExtend implements Serializable {
    
    private Integer id;

    private Integer age;
    private Integer count;

    /**
     *
     */
    private ObjectId schoolId;
}
