//package org.lisang.flash_sale.config.shiro;
//
//
//import org.apache.shiro.authc.AuthenticationToken;
//
//public class JwtToken implements AuthenticationToken {
//
//    private String username;
//
//    private String password;
//
//    private String token;
//    public JwtToken(String token) {
//        this.token = token;
//    }
//
//    public JwtToken(String username, String password){
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return token;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
//}
