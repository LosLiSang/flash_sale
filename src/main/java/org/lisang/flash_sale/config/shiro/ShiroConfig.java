//package org.lisang.flash_sale.config.shiro;
//
//import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
//import org.apache.shiro.mgt.DefaultSubjectDAO;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
////@Configuration
//public class ShiroConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SessionManager webSessionManager(RedisSessionDAO redisSessionDAO) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO);
//        return sessionManager;
//    }
//    @Bean
//    public DefaultWebSecurityManager webSecurityManager(@Qualifier("userRealm") UserRealm userRealm,
//                                                        JwtCredentialsMatcher jwtCredentialsMatcher,
//                                                        SessionManager webSessionManager,
//                                                     RedisCacheManager redisCacheManager) {
//        userRealm.setCredentialsMatcher(jwtCredentialsMatcher);
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(userRealm);
//        securityManager.setSessionManager(webSessionManager);
//        securityManager.setCacheManager(redisCacheManager);
//        /*
//         * 关闭shiro自带的session，详情见文档
//         */
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);
//        return securityManager;
//    }
//
//
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/api/**", "jwt"); // 主要通过注解方式校验权限
//        chainDefinition.addPathDefinitions(filterMap);
//        return chainDefinition;
//    }
//
//    @Bean("shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("webSecurityManager") SecurityManager webSecurityManager,
//                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("jwt", jwtFilter);
//        shiroFilterFactoryBean.setFilters(filters);
//
//        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        return shiroFilterFactoryBean;
//    }
//
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//    @Bean
//    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
//        return creator;
//    }
//
//
//
//}
