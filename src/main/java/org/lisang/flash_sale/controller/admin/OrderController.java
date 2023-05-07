package org.lisang.flash_sale.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.param.PageOrderParam;
import org.lisang.flash_sale.domain.vo.OrderVO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "订单模块")
@RequestMapping("/admin/order")
public class OrderController extends BaseController<OrderService, OrderPO> {


    @Autowired
    private OrderService orderService;

    @Auth
    @ApiOperation("分页查询全部订单")
    @PostMapping("page")
    public ResultVO page(HttpServletRequest servletRequest,@RequestBody SmartPageVO<PageOrderParam> param) {
        Page<PageOrderParam> page = param.initPage();
        QueryWrapper wrapper = new QueryWrapper();
        String activityName = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageOrderParam::getActivityName)
                .orElse("");
        String username = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageOrderParam::getUsername)
                .orElse("");
        List<OrderStatusEnum> orderStatusEnums = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageOrderParam::getPayStateList)
                .orElse(new ArrayList<>());
        wrapper.like("user.username", username);
        wrapper.like("activity.activity_name", activityName);
        // TODO 暂未处理排序
        wrapper.orderByDesc("`order`.update_time");
        boolean isApply = false;
        for(OrderStatusEnum status : orderStatusEnums){
            if(OrderStatusEnum.CREATED.equals(status)){
                if(isApply) wrapper.or();
                wrapper.eq("`order`.pay_status", OrderStatusEnum.CREATED);
                isApply = true;
            }
            else if(OrderStatusEnum.CANCELED.equals(status)){
                if(isApply) wrapper.or();
                wrapper.eq("`order`.pay_status", OrderStatusEnum.CANCELED);
                isApply = true;
            }
            else if(OrderStatusEnum.PAYED.equals(status)){
                if(isApply) wrapper.or();
                wrapper.eq("`order`.pay_status", OrderStatusEnum.PAYED);
                isApply = true;
            }
            else if(OrderStatusEnum.TIMEOUT.equals(status)){
                if(isApply) wrapper.or();
                wrapper.eq("`order`.pay_status", OrderStatusEnum.TIMEOUT);
                isApply = true;
            }
        }
        Page<OrderVO> resPage = orderService.pageOrderVO(page, wrapper);
        return ResultVO.ok(resPage);
    }

}
