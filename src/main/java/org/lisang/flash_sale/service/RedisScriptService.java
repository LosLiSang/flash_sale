package org.lisang.flash_sale.service;

import org.lisang.flash_sale.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisScriptService {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean rollBackStock(String activityId){
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/rollBackStock.lua")));
        redisScript.setResultType(Boolean.class);
        Boolean flag = (Boolean) redisTemplate.execute(redisScript,
                Collections.singletonList(Constants.REDIS_STOCK_PREFIX + activityId),
                Constants.REDIS_STOCK_SALE_COUNT);
        return flag;
    }

}
