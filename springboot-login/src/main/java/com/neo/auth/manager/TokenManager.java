package com.neo.auth.manager;

import com.neo.auth.model.TokenModel;

/**
 * token style
 * request header：authorization: token xxxxx
 */
public interface TokenManager {

    /**
     * 创建token
     * @param userId
     * @return
     */
    TokenModel createToken(String userId);

    /**
     * 检查token
     * @param tokenModel
     * @return
     */
    boolean checkToken(TokenModel tokenModel);

    /**
     * 根据加密字符串获取token
     * @param authorization
     * @return
     */
    TokenModel getToken(String authorization);

    /**
     * 删除token
     * @param userId
     */
    void deleteToken(String userId);

}
