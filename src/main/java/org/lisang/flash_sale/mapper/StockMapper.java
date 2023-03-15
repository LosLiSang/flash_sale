package org.lisang.flash_sale.mapper;

import org.lisang.flash_sale.domain.po.StockPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisang
 * @since 2023-03-14
 */
@Mapper
public interface StockMapper extends BaseMapper<StockPO> {

}
