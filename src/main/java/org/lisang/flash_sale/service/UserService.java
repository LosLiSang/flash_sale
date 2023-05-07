package org.lisang.flash_sale.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.domain.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lisang.flash_sale.domain.vo.UserDetailVO;
import org.lisang.flash_sale.domain.vo.UserInfoListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
public interface UserService extends IService<UserPO> {

    Page<UserInfoListVO> pageUserInfoList(Page page, Wrapper wrapper);


    UserDetailVO getUserDetailByToken();
}
