package com.uu.anhusky.config.commons;

import com.alibaba.fastjson.JSON;
import com.uu.anhusky.config.annotation.ControllerLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @author anhusky
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.uu.anhusky.config.annotation.ControllerLog)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String classMethod = joinPoint.getSignature().toString();
        String classMethodParams = Arrays.toString(joinPoint.getArgs());

        log.info("===============请求 start===============");
        log.info("请求描述：" + getControllerMethodDescription(joinPoint));
        log.info("来源地址：" + ip);
        log.info("请求地址：" + url);
        log.info("请求方式：" + method);
        log.info("请求类方法：" + classMethod);
        log.info("请求类方法参数：" + classMethodParams);

    }

    @AfterReturning(returning = "object", pointcut = "controllerAspect()")
    public void doAfterReturing(Object object) {
        log.info("返回内容：" + JSON.toJSONString(object));
        log.info("===============请求 end===============");

    }

    @AfterThrowing(throwing = "ex", pointcut = "controllerAspect()")
    public void doAfterThrowing(Throwable ex) {
        log.error("异常内容：", ex);
        log.info("===============请求 end===============");

    }

    /**
     * 获取方法描述信息
     *
     * @param joinPoint jp
     * @return string
     * @throws Exception ex
     */
    private static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLog.class).value();
                    break;
                }
            }
        }
        return description;
    }
}
