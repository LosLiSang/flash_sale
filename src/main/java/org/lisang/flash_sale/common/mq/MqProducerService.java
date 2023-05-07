package org.lisang.flash_sale.common.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqProducerService {

    @Value("${rocketmq.send-message-timeout}")
    private Integer messageTimeOut;

    // 建议正常规模项目统一用一个TOPIC
    @Value("${rocketmq.topic}")
    private static String topic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;




}
