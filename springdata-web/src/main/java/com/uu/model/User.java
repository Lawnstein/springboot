package com.uu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-16 10:50
 **/
@Data
@NoArgsConstructor
@Component
public class User {
    @Value("${com.neo.title}")
    private static String title;
    @Value("${com.neo.description}")
    private static String description;

    private String name;
    private int age;
    private int sex;
    private  String value = title + description;

    private String toll;
    private String color;

    private String email;
    private String QQ;

    public User(String value, String toll, String color, String email, String QQ) {
        this.value = value;
        this.toll = toll;
        this.color = color;
        this.email = email;
        this.QQ = QQ;
    }
}
