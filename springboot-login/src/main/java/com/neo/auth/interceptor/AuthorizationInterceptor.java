package com.neo.auth.interceptor;


import com.alibaba.fastjson.JSON;
import com.neo.auth.annotation.Authorization;
import com.neo.auth.manager.TokenManager;
import com.neo.auth.model.TokenModel;
import com.neo.config.Constants;
import com.neo.model.ResultModel;
import com.neo.model.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TokenManager redisTokenManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(Authorization.class) != null) {
            String authorization = request.getHeader(Constants.AUTHORIZATION);

            TokenModel tokenModel = redisTokenManager.getToken(authorization);
            if (redisTokenManager.checkToken(tokenModel)) {
                request.setAttribute(Constants.CURRENT_USER_ID, tokenModel.getUserId());
                // with api access controll
                String api = request.getMethod() + ":" + request.getRequestURI();

                logger.info("请求地址：" + api);

                return true;

            }
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(JSON.toJSONString(new ResultModel(ResultStatus.NEED_PERMISSION)));
            return false;
        }

        return true;
    }

}
