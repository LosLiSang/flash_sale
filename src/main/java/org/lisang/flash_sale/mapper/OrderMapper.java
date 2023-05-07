package org.lisang.flash_sale.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.lisang.flash_sale.domain.dto.OrderUserActivityDTO;
import org.lisang.flash_sale.domain.po.OrderPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisang
 * @since 2023-03-14
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderPO> {

    Page<OrderUserActivityDTO> pageOrderUserActivityDTO(Page page, @Param(Constants.WRAPPER) Wrapper queryWrapper);
    OrderUserActivityDTO getUserActivityDTOById(@Param(Constants.WRAPPER) Wrapper queryWrapper);




}
