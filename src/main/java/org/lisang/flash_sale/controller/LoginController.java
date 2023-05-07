package org.lisang.flash_sale.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.param.LoginParam;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.service.UserService;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.JwtTokenTools;
import org.lisang.flash_sale.util.RedisTools;
import org.lisang.flash_sale.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "登入模块")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTools redisTools;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginParam loginParam) throws BizException {
        UserPO userPO = userService.getOne(new QueryWrapper<UserPO>()
                .eq("username", loginParam.getUsername()));
        if(userPO == null)
            throw new BizException(BizExceptionCodeEnum.LOGIN_ACCOUNT_NOT_EXIST);
        if(!userPO.getPassword().equals(loginParam.getPassword()))
            throw new BizException(BizExceptionCodeEnum.LOGIN_PASSWORD_ERROR);
        String token = JwtTokenTools.generateToken(userPO.getId(), userPO.getUsername());
        redisTools.set(Constants.REDIS_TOKEN_PREFIX + userPO.getId(),
                token,
                Constants.EXPIRE_SECOND);
        // TODO 禁止在redis中存取用户密码等敏感信息
        redisTools.set(Constants.REDIS_USER_PREFIX + userPO.getId(),
                JsonTools.toJSONString(userPO),
                Constants.EXPIRE_SECOND);
        return ResultVO.ok(token);
    }

    @ApiOperation("登出接口")
    @PostMapping("/logout")
    @Auth
    public ResultVO logout(HttpServletRequest servletRequest){
        UserPO userPO = ThreadLocalUtil.getCurrentUser();
        redisTools.del(Constants.REDIS_TOKEN_PREFIX + userPO.getId());
        redisTools.del(Constants.REDIS_USER_PREFIX + userPO.getId());
        return ResultVO.ok();
    }

}
