package org.lisang.flash_sale.controller.downstream;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.service.ActivityService;
import org.lisang.flash_sale.service.OrderService;
import org.lisang.flash_sale.service.RedisScriptService;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.RedisTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
public class MQConsumerService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisTools redisTools;

    @Autowired
    private RedisScriptService redisScriptService;

    @Service
    @RocketMQMessageListener(
            consumerGroup = "FLASH_SALE_ORDER_CONSUMER",
            topic = Constants.MQ_TOPIC,
            selectorExpression = Constants.MQ_ORDER_TAG,
            selectorType = SelectorType.TAG)
    public class OrderConsumer implements RocketMQListener<OrderPO> {
        // 保存订单
        @Override
        public void onMessage(OrderPO orderPO) {
            log.info("保存消息 orderId_{}", orderPO.getId());
            orderService.saveOrUpdate(orderPO);
            ActivityPO activityPO = JsonTools.parseJSONStr2T((String) redisTools.get(Constants.REDIS_ACTIVITY_PREFIX + orderPO.getActivityId()),
                    ActivityPO.class);
            String saleCountString = (String) redisTools.hget(Constants.REDIS_STOCK_PREFIX + activityPO.getId(), Constants.REDIS_STOCK_SALE_COUNT);
            if (saleCountString == null) {
                log.warn("Activity_{} sale count is null", activityPO.getId());
                return;
            }
            log.info("Activity_{} sale count is {}", activityPO.getId(), saleCountString);
            Long saleCount = Long.valueOf(saleCountString);
            activityPO.setSaleCount(saleCount);
            activityService.updateById(activityPO);
        }
    }

    @Service
    @RocketMQMessageListener(
            consumerGroup = "FLASH_SALE_ORDER_CHECK_CONSUMER",
            topic = Constants.MQ_TOPIC,
            selectorExpression = Constants.MQ_ORDER_STATUS_CHECK_TAG,
            selectorType = SelectorType.TAG)
    public class OrderStatusCheckConsumer implements RocketMQListener<String> {
        // 延时支付
        @Override
        public void onMessage(String orderId) {
            log.info("检查消息 orderId_{}", orderId);
            OrderPO orderPO = JsonTools.parseJSONStr2T((String) redisTools.get(Constants.REDIS_ORDER_PREFIX + orderId),
                    OrderPO.class);
            if (orderPO == null) {
                log.warn("Order in redis is null");
                return;
            }
            String activityId = orderPO.getActivityId();
            if (orderPO.getPayStatus().equals(OrderStatusEnum.CREATED)) {
                redisScriptService.rollBackStock(activityId);
                orderPO.setPayStatus(OrderStatusEnum.TIMEOUT);
                redisTools.set(Constants.REDIS_ORDER_PREFIX + orderId, JsonTools.toJSONString(orderPO), Constants.EXPIRE_SECOND);
                rocketMQTemplate.convertAndSend(Constants.MQ_TOPIC + ":" + Constants.MQ_ORDER_TAG,
                        orderPO);
            }
        }
    }

}
