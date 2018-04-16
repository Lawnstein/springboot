package com.neo.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-20 17:35
 **/
@Component
@Aspect
public class WebControllerAop {

    @Pointcut("execution(* com.neo.web..*.*(..))")
    public void executeService(){
            
    }

    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
         System.out.println("前置通知--- + " + joinPoint.getSignature().getName()   );
    }

}
