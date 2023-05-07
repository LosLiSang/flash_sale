package org.lisang.flash_sale.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.SysFilePO;
import org.lisang.flash_sale.service.SysFileService;
/**
 * <p>
 * 系统文件 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "系统文件模块")
@RequestMapping("/admin/sysFile")
public class SysFileController extends BaseController<SysFileService, SysFilePO> {

}
