//package org.lisang.flash_sale.config.shiro;
//
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
//import org.lisang.flash_sale.common.Constants;
//import org.lisang.flash_sale.util.JwtTokenTools;
//import org.lisang.flash_sale.util.RedisTools;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class JwtCredentialsMatcher extends SimpleCredentialsMatcher {
//
//    @Autowired
//    private RedisTools redisTools;
//
//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        JwtToken jwtToken = (JwtToken) token;
//        String tokenPwd = new String(jwtToken.getPassword());
//        String infoPwd = (String) info.getCredentials();
//        UserProfile userProfile = (UserProfile) info.getPrincipals().getPrimaryPrincipal();
//        String userId = userProfile.getId();
//        String username = userProfile.getUsername();
//        if(super.equals(tokenPwd, infoPwd)){
//            jwtToken.setToken(JwtTokenTools.generateToken(userId, username));
//            redisTools.set(Constants.REDIS_TOKEN_PREFIX + userId,
//                    jwtToken.getToken(),
//                    Constants.EXPIRE_SECOND);
//            return true;
//        }else {
//            return false;
//        }
//
//    }
//
//}
