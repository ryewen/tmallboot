package com.loststars.tmallboot.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    @Pointcut("execution(public * com.loststars.tmallboot.service.A.*(..))")
    public void log() {
        
    }
    
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("log");
    }
}
