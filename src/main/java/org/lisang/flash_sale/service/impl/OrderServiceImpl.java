package org.lisang.flash_sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.dto.OrderUserActivityDTO;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.po.OrderPO;
import org.lisang.flash_sale.domain.vo.OrderVO;
import org.lisang.flash_sale.domain.vo.UserOrderPayVO;
import org.lisang.flash_sale.domain.vo.UserOrderVO;
import org.lisang.flash_sale.mapper.OrderMapper;
import org.lisang.flash_sale.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    public void cancelOrder(OrderPO orderPO){

    }


    @Override
    public void checkOrderStatus(OrderPO orderPO) {
        if(orderPO.getPayStatus().equals(OrderStatusEnum.CANCELED))
            throw new BizException(BizExceptionCodeEnum.ORDER_STATUS_ALREADY_CANCELED);
        if(orderPO.getPayStatus().equals(OrderStatusEnum.PAYED))
            throw new BizException(BizExceptionCodeEnum.ORDER_STATUS_ALREADY_PAYED);
        if(orderPO.getPayStatus().equals(OrderStatusEnum.TIMEOUT))
            throw new BizException(BizExceptionCodeEnum.ORDER_STATUS_ERROR);
    }

    @Override
    public Page<UserOrderVO> pageUserOrderVO(Page page, Wrapper wrapper) {
        Page<OrderUserActivityDTO> dtoPage = this.getBaseMapper().pageOrderUserActivityDTO(page, wrapper);
        Page<UserOrderVO> userOrderVOPage = new Page<>();
        BeanUtils.copyProperties(dtoPage, userOrderVOPage);
        List<UserOrderVO> orderVORecords = dtoPage.getRecords().stream().map(orderUserActivityDTO -> {
            UserOrderVO order = new UserOrderVO();
            BeanUtils.copyProperties(orderUserActivityDTO, order);
            return order;
        }).collect(Collectors.toList());
        userOrderVOPage.setRecords(orderVORecords);
        return userOrderVOPage;
    }

    @Override
    public Page<OrderVO> pageOrderVO(Page page, Wrapper wrapper) {
        Page<OrderUserActivityDTO> dtoPage = this.getBaseMapper().pageOrderUserActivityDTO(page, wrapper);
        Page<OrderVO> orderVOPage = new Page<>();
        BeanUtils.copyProperties(dtoPage, orderVOPage);
        dtoPage.getRecords().stream().map(orderUserActivityDTO -> {
            OrderPO orderPO = new OrderPO();
            BeanUtils.copyProperties(orderUserActivityDTO, orderPO);
            return orderPO;
        });
        return orderVOPage;
    }

    @Override
    public UserOrderPayVO getUserOrderPayVO(String id) {
        if(id == null) throw new BizException(BizExceptionCodeEnum.PARAM_EXCEPTION);
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("`order`.id", id);
        OrderUserActivityDTO dto = this.getBaseMapper().getUserActivityDTOById(wrapper);
        UserOrderPayVO userOrderPayVO = new UserOrderPayVO();
        BeanUtils.copyProperties(dto, userOrderPayVO);
        return userOrderPayVO;
    }
}
