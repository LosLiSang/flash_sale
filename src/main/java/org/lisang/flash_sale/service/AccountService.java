package org.lisang.flash_sale.service;

import org.lisang.flash_sale.domain.po.AccountPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
public interface AccountService extends IService<AccountPO> {

    boolean pay(String activityId);

}
