package org.lisang.flash_sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.netty.util.Constant;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.po.AccountPO;
import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.mapper.AccountMapper;
import org.lisang.flash_sale.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lisang.flash_sale.service.OrderService;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.RedisTools;
import org.lisang.flash_sale.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
@Service
@Transactional
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountPO> implements AccountService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTools redisTools;

    @Override
    public boolean pay(String activityId) {
        ActivityPO activityPO = JsonTools.parseJSONStr2T((String) redisTools.get(Constants.REDIS_ACTIVITY_PREFIX + activityId),
                ActivityPO.class);
        String userId = ThreadLocalUtil.getCurrentUser().getId();
        AccountPO accountPO = getOne(new QueryWrapper<AccountPO>().eq("user_id", userId));
        BigDecimal balance = accountPO.getBalance();
        BigDecimal flashPrice = activityPO.getFlashPrice();
        if(balance.compareTo(flashPrice) > 0){
            accountPO.setBalance(balance.subtract(flashPrice));
            updateById(accountPO);
        }
        else throw new BizException(BizExceptionCodeEnum.ACCOUNT_BALANCE_LOW);
        return false;
    }
}
