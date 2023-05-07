package org.lisang.flash_sale.controller.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.param.CancelOrderParam;
import org.lisang.flash_sale.domain.param.CreateOrderParam;
import org.lisang.flash_sale.domain.param.PageUserOrderParam;
import org.lisang.flash_sale.domain.param.PayOrderParam;
import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.domain.vo.UserOrderPayVO;
import org.lisang.flash_sale.domain.vo.UserOrderVO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.lisang.flash_sale.service.AccountService;
import org.lisang.flash_sale.service.ActivityService;
import org.lisang.flash_sale.service.OrderService;
import org.lisang.flash_sale.service.RedisScriptService;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.RedisTools;
import org.lisang.flash_sale.util.SnowFlakeIDTools;
import org.lisang.flash_sale.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Api(tags = "下单业务模块")
@RequestMapping("/api/order")
@RestController
@Slf4j
public class OrderServiceController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisTools redisTools;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScriptService redisScriptService;

    @Auth
    @ApiOperation("用户获取订单详情")
    @GetMapping("/detail/{id}")
    public ResultVO get(@PathVariable String id) {
        UserOrderPayVO userOrderPayVO = orderService.getUserOrderPayVO(id);

        return ResultVO.ok(userOrderPayVO);
    }


    @Auth
    @ApiOperation("用户获取订单列表")
    @PostMapping("/page")
    public ResultVO pageOrders(HttpServletRequest servletRequest, @RequestBody SmartPageVO<PageUserOrderParam> param) {
        Page page = param.initPage();

        String activityName = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageUserOrderParam::getActivityName)
                .orElse("");
        List<OrderStatusEnum> statusList = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageUserOrderParam::getPayStateList)
                .orElse(new ArrayList<>());

        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.like("activity_name", activityName);
        // TODO 暂未处理排序
        wrapper.orderByDesc("`order`.update_time");
        boolean isApply = false;
        for (OrderStatusEnum statusEnum : statusList) {
            if (OrderStatusEnum.CREATED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq("pay_status", OrderStatusEnum.CREATED);
                isApply = true;
            } else if (OrderStatusEnum.CANCELED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq("pay_status", OrderStatusEnum.CANCELED);
                isApply = true;
            } else if (OrderStatusEnum.PAYED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq("pay_status", OrderStatusEnum.PAYED);
                isApply = true;
            }else if (OrderStatusEnum.TIMEOUT.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq("pay_status", OrderStatusEnum.TIMEOUT);
                isApply = true;
            }
        }
        Page<UserOrderVO> resPage = orderService.pageUserOrderVO(page, wrapper);
        return ResultVO.ok(resPage);
    }

    @Auth
    @ApiOperation("创建订单（下单）")
    @PostMapping("/create")
    @Transactional
    public ResultVO createOrder(HttpServletRequest servletRequest, @RequestBody CreateOrderParam param) throws Exception {
        String activityId = param.getActivityId();
        // 线程用户
        UserPO currentUser = ThreadLocalUtil.getCurrentUser();
        String orderId = SnowFlakeIDTools.generateId();

        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(param, orderPO);
        orderPO.setUserId(currentUser.getId());
        orderPO.setPayStatus(OrderStatusEnum.CREATED);
        orderPO.setId(orderId);
        // 时间保证
        orderPO.setCreateTime(LocalDateTime.now());
        // 预扣库存
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/delStock.lua")));
        redisScript.setResultType(Boolean.class);
        Boolean flag = (Boolean) redisTemplate.execute(redisScript,
                Collections.singletonList(Constants.REDIS_STOCK_PREFIX + activityId),
                Constants.REDIS_STOCK_ALL_COUNT,
                Constants.REDIS_STOCK_SALE_COUNT);
        if (!flag) {
            throw new BizException(BizExceptionCodeEnum.STOCK_MISS);
        }
        // 写缓存
        redisTools.set(Constants.REDIS_ORDER_PREFIX + orderId, JsonTools.toJSONString(orderPO), Constants.EXPIRE_SECOND);
        // 订单延迟支付
        rocketMQTemplate.syncSend(Constants.MQ_TOPIC + ":" + Constants.MQ_ORDER_STATUS_CHECK_TAG,
                MessageBuilder.withPayload(orderPO.getId()).build(),
                3000,
                5); // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h TODO
        // 数据库后写
        rocketMQTemplate.convertAndSend(Constants.MQ_TOPIC + ":" + Constants.MQ_ORDER_TAG,
                orderPO);
        return ResultVO.ok(orderPO);
    }

    @Auth
    @ApiOperation("取消订单")
    @PostMapping("/cancel")
    @Transactional
    public ResultVO cancelOrder(HttpServletRequest servletRequest, @RequestBody CancelOrderParam param) {

        String orderId = param.getOrderId();
        OrderPO orderPO = JsonTools.parseJSONStr2T((String) redisTools.get(Constants.REDIS_ORDER_PREFIX + orderId),
                OrderPO.class);
        // 检查订单
        orderService.checkOrderStatus(orderPO);
        orderPO.setPayStatus(OrderStatusEnum.CANCELED);
        String activityId = orderPO.getActivityId();
        // redis库存回滚
        redisScriptService.rollBackStock(activityId);
        // 写redis
        redisTools.set(Constants.REDIS_ORDER_PREFIX + orderId, JsonTools.toJSONString(orderPO), Constants.EXPIRE_SECOND);
        // 数据库后写
        rocketMQTemplate.convertAndSend(Constants.MQ_TOPIC + ":" + Constants.MQ_ORDER_TAG, orderPO);
        return ResultVO.ok(orderPO);
    }

    @Auth
    @Transactional
    @ApiOperation("支付接口")
    @PostMapping("/pay")
    public ResultVO pay(HttpServletRequest servletRequest, @RequestBody PayOrderParam param) {
        String orderId = param.getOrderId();
        OrderPO orderPO = JsonTools.parseJSONStr2T((String) redisTools.get(Constants.REDIS_ORDER_PREFIX + orderId),
                OrderPO.class);
        // 检查订单
        orderService.checkOrderStatus(orderPO);
        orderPO.setPayStatus(OrderStatusEnum.PAYED);
        String activityId = orderPO.getActivityId();
        // 扣款 TODO 消息队列
        accountService.pay(activityId);
        // 写redis
        redisTools.set(Constants.REDIS_ORDER_PREFIX + orderId, JsonTools.toJSONString(orderPO), Constants.EXPIRE_SECOND);
        // 数据库后写
        rocketMQTemplate.convertAndSend(Constants.MQ_TOPIC + ":" + Constants.MQ_ORDER_TAG, orderPO);
        return ResultVO.ok(orderPO);
    }

}
