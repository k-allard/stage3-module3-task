package com.mjc.school.service.congiguration;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {

    //the execution of any method defined in the service.impl package
    @Before("execution(* com.mjc.school.service.impl.*.*(..))")
    public void advice() {
        System.out.println("This is from MyAspect");
    }
}