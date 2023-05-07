//package org.lisang.flash_sale.config.shiro;
//
//
//import com.auth0.jwt.interfaces.Claim;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.lisang.flash_sale.common.Constants;
//import org.lisang.flash_sale.common.exception.BizException;
//import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
//import org.lisang.flash_sale.domain.po.UserPO;
//import org.lisang.flash_sale.service.UserService;
//import org.lisang.flash_sale.util.JwtTokenTools;
//import org.lisang.flash_sale.util.RedisTools;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//
//@Component
//public class UserRealm extends AuthorizingRealm {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RedisTools redisTools;
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JwtToken;
//    }
//
//
//    // TODO 授权
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        return null;
//    }
//
//    // 认证
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException,BizException {
//        JwtToken jwtToken = (JwtToken) token;
//        QueryWrapper<UserPO> loginWrapper = new QueryWrapper<>();
//        loginWrapper.eq("username", jwtToken.getUsername());
//        UserPO userPO = userService.getOne(loginWrapper);
//        // 账户不存在
//        if(userPO == null) {
//            throw new UnknownAccountException();
//        }
//
//
////        Map claims = JwtTokenTools.getClaims((String) jwt.getPrincipal());
////        System.out.println(claims.get("userId"));
////        Claim claim = (Claim) claims.get("userId");
////        String userId = claim.asString();
//
//        UserProfile profile = new UserProfile();
//        BeanUtils.copyProperties(userPO, profile);
//        return new SimpleAuthenticationInfo(profile, userPO.getPassword(), getName());
//    }
//}
