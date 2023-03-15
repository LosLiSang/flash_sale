package org.lisang.flash_sale.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BaseModelFieldAspect {


    // 新增 更新
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void insertOrUpdatePointCut(){

    }


}
