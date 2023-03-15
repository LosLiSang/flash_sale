package org.lisang.flash_sale.util;


import java.time.LocalDateTime;
import java.util.UUID;

public class TokenTools {

    public static String generateToken(String userId){
        String plainStr = userId + LocalDateTime.now();
        String uuid = UUID.nameUUIDFromBytes(plainStr.getBytes()).toString().trim().replaceAll("-", "");
        return uuid.toString();
    }

    public static void main(String[] args) {
        System.out.println(TokenTools.generateToken("12312"));
    }
}
