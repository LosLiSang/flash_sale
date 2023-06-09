//package org.lisang.flash_sale.config.shiro;
//
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.auth0.jwt.interfaces.Claim;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.lisang.flash_sale.common.Constants;
//import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
//import org.lisang.flash_sale.domain.vo.base.ResultVO;
//import org.lisang.flash_sale.util.JsonTools;
//import org.lisang.flash_sale.util.JwtTokenTools;
//import org.lisang.flash_sale.util.RedisTools;
//import org.lisang.flash_sale.util.ResponseExceptionHandelTools;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//public class JwtFilter extends AuthenticatingFilter {
//    @Autowired
//    private RedisTools redisTools;
//
//    @Override
//    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        // 获取 token
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String jwt = request.getHeader("Authorization");
//        if(StringUtils.isEmpty(jwt)){
//            return null;
//        }
//        return new JwtToken(jwt);
//    }
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String token = request.getHeader("Authorization");
//        if(StringUtils.isEmpty(token)) {
//            ResponseExceptionHandelTools.error(BizExceptionCodeEnum.TOKEN_NEEDED_EXCEPTION, servletResponse);
//            return false;
//        } else {
//            // 判断是否已过期
//            Map claims;
//            try{
//                claims = JwtTokenTools.getClaims(token);
//            }catch (TokenExpiredException e){
//                ResponseExceptionHandelTools.error(BizExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION, servletResponse);
//                return false;
//            }
//            if(claims != null){
//                Claim claim = (Claim) claims.get("userId");
//                if(redisTools.hasKey(Constants.REDIS_TOKEN_PREFIX + claim.asString())){
//                    String s = (String) redisTools.get(Constants.REDIS_TOKEN_PREFIX + claim.asString());
//                    if(token.equals(s))
//                        return true;
//                }
//            }
//        }
//        ResponseExceptionHandelTools.error(BizExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION, servletResponse);
//        return false;
//    }
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        try {
//            //处理登录失败的异常
//            Throwable throwable = e.getCause() == null ? e : e.getCause();
//            ResultVO r = ResultVO.error(throwable.getMessage());
//            String json = JsonTools.toJSONString(r);
//            httpResponse.getWriter().print(json);
//        } catch (IOException e1) {
//        }
//        return false;
//    }
//    /**
//     * 对跨域提供支持
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }
//}