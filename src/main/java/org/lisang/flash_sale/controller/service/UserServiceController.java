package org.lisang.flash_sale.controller.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.domain.param.PageUserParam;
import org.lisang.flash_sale.domain.vo.UserDetailVO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.lisang.flash_sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/api/user")
public class UserServiceController {

    @Autowired
    private UserService userService;

    @Auth
    @ApiOperation("查询用户详情")
    @GetMapping("detail")
    public ResultVO page(HttpServletRequest servletRequest){
        UserDetailVO userDetail = userService.getUserDetailByToken();
        return ResultVO.ok(userDetail);
    }


}
