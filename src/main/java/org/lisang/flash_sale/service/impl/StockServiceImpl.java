package org.lisang.flash_sale.service.impl;

import org.lisang.flash_sale.domain.po.StockPO;
import org.lisang.flash_sale.mapper.StockMapper;
import org.lisang.flash_sale.service.IStockService;
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
public class StockServiceImpl extends ServiceImpl<StockMapper, StockPO> implements IStockService {

}
