package org.lisang.flash_sale.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.SysDictItemPO;
import org.lisang.flash_sale.service.SysDictItemService;
/**
 * <p>
 * 系统字典项 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "系统字典项模块")
@RequestMapping("/admin/sysDictItem")
public class SysDictItemController extends BaseController<SysDictItemService, SysDictItemPO> {

}
