package org.lisang.flash_sale.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.domain.po.OrderPO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lisang.flash_sale.domain.vo.OrderVO;
import org.lisang.flash_sale.domain.vo.UserOrderPayVO;
import org.lisang.flash_sale.domain.vo.UserOrderVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
public interface OrderService extends IService<OrderPO> {

    void checkOrderStatus(OrderPO orderPO);

    Page<UserOrderVO> pageUserOrderVO(Page page, Wrapper wrapper);
    Page<OrderVO> pageOrderVO(Page page, Wrapper wrapper);

    UserOrderPayVO getUserOrderPayVO(String id);
}
