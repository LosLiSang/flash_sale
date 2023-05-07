package org.lisang.flash_sale;

import org.junit.jupiter.api.Test;
import org.lisang.flash_sale.domain.enums.UserRoleEnum;
import org.lisang.flash_sale.domain.po.SysDictPO;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.service.UserService;
import org.lisang.flash_sale.util.JwtTokenTools;
import org.lisang.flash_sale.util.SnowFlakeIDTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class FlashSaleApplicationTests {


    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    void addUser(){
        UserPO userPO = new UserPO();
        userPO.setId("2");
        userPO.setRole(UserRoleEnum.ADMIN);
        userPO.setPassword("1");
        userPO.setPhone("1");
        userPO.setUsername("1");
        userService.save(userPO);
    }


    @Test
    void updateUser(){
        UserPO userPO = new UserPO();
        userPO.setId("2");
        userPO.setRole(UserRoleEnum.CLIENT);
        userPO.setPassword("2");
        userPO.setPhone("2");
        userPO.setUsername("2");
        userService.updateById(userPO);
    }

    @Test
    void addDict(){
        SysDictPO sysDictPO = new SysDictPO();
        sysDictPO.setDesc("订单状态");
        sysDictPO.setName("OrderStatus");
        sysDictPO.setId(SnowFlakeIDTools.generateId());
    }

    @Test
    void testLogin(){

    }

    @Test
    void testJwt(){
        System.out.println(JwtTokenTools.generateToken("123", "admin"));
        Map claims = JwtTokenTools.getClaims(JwtTokenTools.generateToken("123", "admin"));
        System.out.println(claims);
    }
}
