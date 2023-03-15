package org.lisang.flash_sale.controller.admin;

import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.service.ActivityService;
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
@RequestMapping("/activityPO")
public class ActivityController extends BaseController<ActivityService, ActivityPO> {

}
