package org.lisang.flash_sale.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.AccountPO;
import org.lisang.flash_sale.service.AccountService;
/**
 * <p>
 * 账户 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "账户模块")
@RequestMapping("/admin/account")
public class AccountController extends BaseController<AccountService, AccountPO> {

}
