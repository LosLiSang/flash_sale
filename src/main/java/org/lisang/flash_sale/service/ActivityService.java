package org.lisang.flash_sale.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.domain.po.ActivityPO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lisang.flash_sale.domain.vo.UserActivityListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
public interface ActivityService extends IService<ActivityPO> {

    Page<UserActivityListVO> pageUserActivityListVO(Page page, Wrapper wrapper);
}
