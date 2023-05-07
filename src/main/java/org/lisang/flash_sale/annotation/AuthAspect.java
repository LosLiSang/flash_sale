package org.lisang.flash_sale.annotation;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;


@Configuration
@Aspect
public class AuthAspect {

    @Autowired
    private RedisTools redisTools;

    @Pointcut("@annotation(Auth)")
    public void loginPointCut() {

    }


    @Around(value = "loginPointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new BizException(BizExceptionCodeEnum.TOKEN_NEEDED_EXCEPTION);
        }
        // 初次验证token，并返回携带的userId
        Map<String, Claim> claims = JwtTokenTools.getClaims(token);
        Claim claim = Optional.ofNullable(claims).map(c -> c.get("userId"))
                .orElseThrow(() -> new BizException(BizExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION));
        // 再次验证token，验证redis里的token是否被删除
        String userId = claim.asString();
        Optional.ofNullable(redisTools.get(Constants.REDIS_TOKEN_PREFIX + userId))
                .filter(token::equals)
                .orElseThrow(() -> new BizException(BizExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION));
        // 添加userPO线程变量
        String userPO = (String) redisTools.get(Constants.REDIS_USER_PREFIX + userId);
        ThreadLocalUtil.addCurrentUser(JsonTools.parseJSONStr2T(userPO, UserPO.class));
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            ThreadLocalUtil.remove();
        }
    }

}
