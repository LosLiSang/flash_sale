package org.lisang.flash_sale.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.lisang.flash_sale.domain.dto.UserAccountDTO;
import org.lisang.flash_sale.domain.po.UserPO;
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
public interface UserMapper extends BaseMapper<UserPO> {

    Page<UserAccountDTO> pageUserAccountDTO(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
