package org.lisang.flash_sale.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.StockPO;
import org.lisang.flash_sale.service.StockService;
/**
 * <p>
 * 库存 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "库存模块")
@RequestMapping("/admin/stock")
public class StockController extends BaseController<StockService, StockPO> {

}
