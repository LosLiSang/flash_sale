package org.lisang.flash_sale.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lisang.flash_sale.domain.param.LoginParam;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.domain.vo.ResultVO;
import org.lisang.flash_sale.service.UserService;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.RedisTools;
import org.lisang.flash_sale.util.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "登入模块")
@RestController("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTools redisTools;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginParam loginParam){
        String password = loginParam.getPassword();
        QueryWrapper<UserPO> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq("username", loginParam.getUsername());

        // TODO BizException

        UserPO userPO = userService.getOne(loginWrapper);
        String token = Constants.REDIS_USER_PREFIX + TokenTools.generateToken(userPO.getUpdateUserId());
        redisTools.set(token, JsonTools.toJSONString(userPO), Constants.EXPIRE_SECOND);
        return ResultVO.ok(token);
    }

}
