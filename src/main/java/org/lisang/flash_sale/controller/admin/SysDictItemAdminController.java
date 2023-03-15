package org.lisang.flash_sale.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/sysDictItemPO")
public class SysDictItemAdminController extends BaseController<SysDictItemService, SysDictItemPO> {

}
