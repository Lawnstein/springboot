package com.neo.auth.manager.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neo.auth.manager.TokenManager;
import com.neo.auth.model.TokenModel;
import com.neo.config.Constants;
import com.neo.properties.JwtTokenProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenManager implements TokenManager {

    @Resource
    private JwtTokenProperties jwtTokenProperties;

    /**
     * 生成 jwt token
     * @param userId user id
     * @return token
     */
    private String createJwtToken(String userId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, Constants.TOKEN_EXPIRES_MINUTE);
        try {
            return JWT.create()
                    .withIssuer(jwtTokenProperties.getIssuer())
                    .withIssuedAt(new Date())
                    .withAudience(jwtTokenProperties.getAudience())
                    .withClaim(jwtTokenProperties.getUserid(), userId)
                    .withExpiresAt(cal.getTime())
                    .sign(Algorithm.HMAC256(jwtTokenProperties.getSecret()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public TokenModel createToken(String userId) {
        String token = createJwtToken(userId);
        redisTemplate.opsForValue().set(userId, token, Constants.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
        return new TokenModel(userId, token);
    }

    @Override
    public boolean checkToken(TokenModel tokenModel) {
        if (tokenModel == null) {
            return false;
        }
        String token = redisTemplate.boundValueOps(tokenModel.getUserId()).get();
        if (token == null || !token.equals(tokenModel.getToken())) {
            return false;
        }
        redisTemplate.boundValueOps(tokenModel.getUserId()).expire(Constants.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public TokenModel getToken(String authorization) {
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        if (!authorization.startsWith(Constants.TOKEN_PRE)
                || authorization.length() < 10) {
            return null;
        }

        String token = authorization.substring(6, authorization.length());

        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtTokenProperties.getSecret()))
                    .withIssuer(jwtTokenProperties.getIssuer()).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            String userId = jwt.getClaim(jwtTokenProperties.getUserid()).asString();

            return new TokenModel(userId, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteToken(String userId) {
        redisTemplate.delete(userId);
    }
}
