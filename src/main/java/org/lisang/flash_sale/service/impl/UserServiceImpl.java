package org.lisang.flash_sale.service.impl;

import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.mapper.UserMapper;
import org.lisang.flash_sale.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {

}
