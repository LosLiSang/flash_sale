package org.lisang.flash_sale.annotation;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;


@Configuration
@Aspect
public class AuthAspect {


    @Pointcut("@annotation(Auth)")
    public void loginPointCut(){

    }


//    @Before(value = "loginPointCut()")


}
