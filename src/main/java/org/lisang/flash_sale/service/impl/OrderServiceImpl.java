package org.lisang.flash_sale.service.impl;

import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.mapper.OrderMapper;
import org.lisang.flash_sale.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {

}
