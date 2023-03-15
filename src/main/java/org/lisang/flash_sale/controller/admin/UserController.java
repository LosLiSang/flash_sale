package org.lisang.flash_sale.controller.admin;

import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/userPO")
public class UserController extends BaseController<UserService, UserPO> {

}
