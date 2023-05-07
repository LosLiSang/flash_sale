package org.lisang.flash_sale.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lisang.flash_sale.domain.po.ActivityPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lisang.flash_sale.domain.vo.UserActivityDetailVO;
import org.lisang.flash_sale.domain.vo.UserActivityListVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisang
 * @since 2023-03-14
 */
@Mapper
public interface ActivityMapper extends BaseMapper<ActivityPO> {

    Page<UserActivityListVO> pageUserActivityList(Page page, @Param(Constants.WRAPPER) Wrapper queryWrapper);


    @Select("")
    UserActivityDetailVO getUserActivityDetailVO(String id);
}
