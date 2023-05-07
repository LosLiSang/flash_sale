package org.lisang.flash_sale.controller.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.enums.ActivityTimeStatusEnum;
import org.lisang.flash_sale.domain.param.PageUserActivityParam;
import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.domain.vo.UserActivityDetailVO;
import org.lisang.flash_sale.domain.vo.UserActivityListVO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.lisang.flash_sale.mapper.ActivityMapper;
import org.lisang.flash_sale.service.ActivityService;
import org.lisang.flash_sale.service.StockService;
import org.lisang.flash_sale.util.RedisTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = "秒杀活动业务模块")
@RequestMapping("/api/activity")
@RestController
public class ActivityServiceController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RedisTools redisTools;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private StockService stockService;

    @Autowired
    private ActivityMapper activityMapper;

    @Auth
    @ApiOperation("分页查询秒杀活动")
    @PostMapping("/page")
    public ResultVO pageActivityList(HttpServletRequest servletRequest, @RequestBody SmartPageVO<PageUserActivityParam> pageVO) {
        // TODO Redis缓存
        Page page = pageVO.initPage();
        String activityName = Optional.ofNullable(pageVO)
                .map(SmartPageVO::getEntity)
                .map(PageUserActivityParam::getActivityName)
                .orElse("");
        LambdaQueryWrapper<ActivityPO> wrapper = new LambdaQueryWrapper();
        wrapper.orderByDesc(ActivityPO::getUpdateTime);
        // TODO 字段同名 排序
        wrapper.eq(ActivityPO::getActivityStatus, ActivityStatusEnum.ONLINE)
                .like(ActivityPO::getActivityName, activityName);
        // 判空
        List<ActivityTimeStatusEnum> statusList = Optional.ofNullable(pageVO)
                .map(SmartPageVO::getEntity)
                .map(PageUserActivityParam::getStatusList)
                .orElseGet(() -> new ArrayList<>());
        //
        boolean isApply = false;
        for (ActivityTimeStatusEnum statusEnum : statusList) {
            if (ActivityTimeStatusEnum.NOT_STARTED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.apply("now() < start_time");
                isApply = true;
            } else if (ActivityTimeStatusEnum.IN_PROGRESS.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.apply("start_time < now() and now() < end_time");
                isApply = true;
            } else if (ActivityTimeStatusEnum.ENDED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.apply("end_time < now()");
                isApply = true;
            }
        }
        Page<UserActivityListVO> resPage = activityService.pageUserActivityListVO(page, wrapper);
        return ResultVO.ok(resPage);
    }

    @Auth
    @ApiOperation("获取秒杀活动详情")
    @GetMapping("/{id}")
    public ResultVO getActivityDetail(HttpServletRequest servletRequest, @PathVariable("id") String id){
        // TODO Redis缓存
        // 获取秒杀活动详情
        ActivityPO activityPO = activityService.getById(id);
        UserActivityDetailVO resVO = new UserActivityDetailVO();
        BeanUtils.copyProperties(activityPO, resVO);
        if (activityPO.getStartTime().isAfter(LocalDateTime.now()))
            resVO.setActivityStatus(ActivityTimeStatusEnum.NOT_STARTED);
        else if (activityPO.getEndTime().isBefore(LocalDateTime.now()))
            resVO.setActivityStatus(ActivityTimeStatusEnum.ENDED);
        else
            resVO.setActivityStatus(ActivityTimeStatusEnum.IN_PROGRESS);
        return ResultVO.ok(resVO);
    }
}
