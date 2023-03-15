package org.lisang.flash_sale.service.impl;

import org.lisang.flash_sale.domain.po.AccountPO;
import org.lisang.flash_sale.mapper.AccountMapper;
import org.lisang.flash_sale.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountPO> implements AccountService {

}
