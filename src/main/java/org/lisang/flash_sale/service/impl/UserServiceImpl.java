package org.lisang.flash_sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.domain.dto.UserAccountDTO;
import org.lisang.flash_sale.domain.po.UserPO;
import org.lisang.flash_sale.domain.vo.UserDetailVO;
import org.lisang.flash_sale.domain.vo.UserInfoListVO;
import org.lisang.flash_sale.mapper.UserMapper;
import org.lisang.flash_sale.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lisang.flash_sale.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    @Override
    public Page<UserInfoListVO> pageUserInfoList(Page page, Wrapper wrapper) {
        Page<UserAccountDTO> userAccountDTOPage = this.getBaseMapper().pageUserAccountDTO(page, wrapper);
        Page<UserInfoListVO> voPage = new Page<>();
        BeanUtils.copyProperties(userAccountDTOPage, voPage);
        List<UserInfoListVO> vos = userAccountDTOPage.getRecords().stream().map(userAccountDTO -> {
            UserInfoListVO userInfoListVO = new UserInfoListVO();
            BeanUtils.copyProperties(userAccountDTO, userInfoListVO);
            return userInfoListVO;
        }).collect(Collectors.toList());
        voPage.setRecords(vos);
        return voPage;
    }

    @Override
    public UserDetailVO getUserDetailByToken() {
        String id = ThreadLocalUtil.getCurrentUser().getId();
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user.id", id);
        Page<UserAccountDTO> userAccountDTOPage = this.getBaseMapper().pageUserAccountDTO(new Page(), wrapper);
        UserAccountDTO dto = Optional.of(userAccountDTOPage.getRecords()).map(userAccountDTOS -> {
            return userAccountDTOS.get(0);
        }).orElseThrow(BizException::new);
        UserDetailVO vo = new UserDetailVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }


}
