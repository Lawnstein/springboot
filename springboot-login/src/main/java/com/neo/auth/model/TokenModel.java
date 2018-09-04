package com.neo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    /**
     * 用户id
     */
    private String userId;

    /**
     * token 字符串
     */
    private String token;

}
