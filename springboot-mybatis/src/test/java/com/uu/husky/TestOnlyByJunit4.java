package com.uu.husky;

import com.uu.domain.User;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 描述：接口文档，帮助类
 *
 * @author uusao
 * @create 2018-03-24 10:16
 **/
public class TestOnlyByJunit4 {


    /**
     * 暴力反射，获取类的私有字段
     */
    @Test
    public void getProperties() {
        System.out.println(getValues(User.class, false));
    }

    /**
     * 多条数据查询  帮助类
     *
     * @param clazz
     * @param containId
     * @return
     */
    public static String getValues(Class clazz, boolean containId) {
        StringBuilder values = new StringBuilder();
        values.append("\"(");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //System.out.println(field.getName());
            String propertyName = field.getName();
            if (propertyName.equals("id")) {
                if (!containId) { //不包含id
                    continue;
                }
            }
            String value = "#{item." + propertyName + "}" + ",";
            if (fields[fields.length - 1].getName().equals(propertyName)) {
                value = "#{item." + propertyName + "}";
            }

            values.append(value);
        }
        values.append(")\"");
        //System.out.println(values.toString());
        return values.toString();
    }

}
