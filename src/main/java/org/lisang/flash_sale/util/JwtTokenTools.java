package org.lisang.flash_sale.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "token-config")
public class JwtTokenTools {

    private static String secret;

    private static long expire;

    private static String header;

    public static String getSecret() {
        return secret;
    }

    public  void setSecret(String secret) {
        JwtTokenTools.secret = secret;
    }

    public static long getExpire() {
        return expire;
    }

    public  void setExpire(long expire) {
        JwtTokenTools.expire = expire;
    }

    public static String getHeader() {
        return header;
    }

    public  void setHeader(String header) {
        JwtTokenTools.header = header;
    }


    public static String generateToken(String userId, String username) {
        Date date = new Date();

        date.setTime(date.getTime() + expire);
        Date expiration = new Date(System.currentTimeMillis() + expire * 1000);

        String token = null;
        try {
            token = JWT.create()
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withExpiresAt(expiration)
                    .sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException e) {
            // TODO 异常处理
            throw new RuntimeException(e);
        }
        return token;
    }

    public static boolean isExpire(String token){
        try {
            DecodedJWT  decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Claim exp = claims.get("exp");
            Date date = exp.asDate();
            return date.before(new Date());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public static Map getClaims(String token){
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            Map<String, Claim> claims = decodedJWT.getClaims();
            return claims;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BizException(BizExceptionCodeEnum.TOKEN_ERROR_EXCEPTION);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw new BizException(BizExceptionCodeEnum.TOKEN_ERROR_EXCEPTION);
        }
    }
    public static void main(String[] args) {
    }
}
