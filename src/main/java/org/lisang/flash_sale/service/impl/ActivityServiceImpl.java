package org.lisang.flash_sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.domain.vo.UserActivityListVO;
import org.lisang.flash_sale.mapper.ActivityMapper;
import org.lisang.flash_sale.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityPO> implements ActivityService {

    @Override
    public Page<UserActivityListVO> pageUserActivityListVO(Page page, Wrapper wrapper) {
        Page<ActivityPO> poPage = this.getBaseMapper().selectPage(page, wrapper);
        Page<UserActivityListVO> voPage = new Page<>();
        BeanUtils.copyProperties(poPage, voPage);
        List<UserActivityListVO> voList = poPage.getRecords().stream().map(activityPO -> {
            UserActivityListVO vo = new UserActivityListVO();
            BeanUtils.copyProperties(activityPO, vo);
            if (activityPO.getStartTime().isAfter(LocalDateTime.now()))
                vo.setActivityStatus(ActivityStatusEnum.NOT_STARTED);
            else if (activityPO.getEndTime().isBefore(LocalDateTime.now()))
                vo.setActivityStatus(ActivityStatusEnum.ENDED);
            else
                vo.setActivityStatus(ActivityStatusEnum.IN_PROGRESS);
            return vo;
        }).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }
}
