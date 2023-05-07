package org.lisang.flash_sale.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.domain.param.PageUserParam;
import org.lisang.flash_sale.domain.vo.UserInfoListVO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "用户管理模块")
@RequestMapping("/admin/user")
public class UserController extends BaseController<UserService, UserPO> {

    @Autowired
    private UserService userService;


    @Auth
    @ApiOperation("分页查询全部用户")
    @PostMapping("page")
    public ResultVO page(HttpServletRequest servletRequest, @RequestBody SmartPageVO<PageUserParam> param){
        Page page = param.initPage();
        QueryWrapper wrapper = new QueryWrapper();
        String username = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageUserParam::getUsername)
                .orElse("");
        String phone = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageUserParam::getPhone)
                .orElse("");
        wrapper.like("user.username", username);
        wrapper.like("user.phone", phone);
        Page<UserInfoListVO> resPage = userService.pageUserInfoList(page, wrapper);
        return ResultVO.ok(resPage);
    }
}
