package org.lisang.flash_sale.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.lisang.flash_sale.annotation.Auth;
import org.lisang.flash_sale.common.Constants;
import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.enums.ActivityTimeStatusEnum;
import org.lisang.flash_sale.domain.param.PageActivityParam;
import org.lisang.flash_sale.domain.param.PageUserActivityParam;
import org.lisang.flash_sale.domain.param.PublishActivityParam;
import org.lisang.flash_sale.domain.param.UpdateActivityParam;
import org.lisang.flash_sale.domain.po.StockPO;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.lisang.flash_sale.domain.vo.base.SmartPageVO;
import org.lisang.flash_sale.service.StockService;
import org.lisang.flash_sale.util.JsonTools;
import org.lisang.flash_sale.util.RedisTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.po.ActivityPO;
import org.lisang.flash_sale.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author lisang
 * @since 2023-03-23
 */
@RestController
@Api(tags = "秒杀活动管理模块")
@RequestMapping("/admin/activity")
public class ActivityController extends BaseController<ActivityService, ActivityPO> {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RedisTools redisTools;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private StockService stockService;


    @ApiOperation("更新秒杀活动")
    @PostMapping("/update")
    @Auth
    public ResultVO update(HttpServletRequest servletRequest, @RequestBody UpdateActivityParam param){
        ActivityPO po = activityService.getById(param.getId());
        BeanUtils.copyProperties(param, po);
        // TODO
        activityService.updateById(po);
        return ResultVO.ok();
    }

    @ApiOperation("查询秒杀活动")
    @PostMapping("/page")
    @Auth
    public ResultVO page(HttpServletRequest servletRequest, @RequestBody SmartPageVO<PageActivityParam>  param){
        String activityName = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageActivityParam::getActivityName)
                .orElse("");
        List<ActivityStatusEnum> statusList = Optional.ofNullable(param)
                .map(SmartPageVO::getEntity)
                .map(PageActivityParam::getStatusList)
                .orElseGet(() -> new ArrayList<>());
        Page page = param.initPage();
        LambdaQueryWrapper<ActivityPO> wrapper = new LambdaQueryWrapper<ActivityPO>();

        wrapper.like(ActivityPO::getActivityName, activityName);
        boolean isApply = false;
        boolean isInProgress = false;
        for (ActivityStatusEnum statusEnum : statusList) {
            if (ActivityStatusEnum.NOT_STARTED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq(ActivityPO::getActivityStatus, ActivityStatusEnum.ONLINE);
                wrapper.apply("now() < start_time");
                isApply = true;
            } else if (ActivityStatusEnum.IN_PROGRESS.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq(ActivityPO::getActivityStatus, ActivityStatusEnum.ONLINE);
                wrapper.apply("start_time < now() and now() < end_time");
                isApply = true;
            } else if (ActivityStatusEnum.ENDED.equals(statusEnum)) {
                if (isApply) wrapper.or();
                wrapper.eq(ActivityPO::getActivityStatus, ActivityStatusEnum.ONLINE);
                wrapper.apply("end_time < now()");
                isApply = true;
            }else if(ActivityStatusEnum.PUBLISHED.equals(statusEnum)){
                if (isApply) wrapper.or();
                wrapper.eq(ActivityPO::getActivityStatus, statusEnum.PUBLISHED);
                isApply = true;
            }else if(ActivityStatusEnum.OFFLINE.equals(statusEnum)){
                if (isApply) wrapper.or();
                wrapper.eq(ActivityPO::getActivityStatus, statusEnum.OFFLINE);
                isApply = true;
            }
        }
        Page resPage = activityService.page(page, wrapper);
        return ResultVO.ok(resPage);
    }


    @ApiOperation("发布一个秒杀活动")
    @PostMapping("/publish")
    @Auth
    public ResultVO publish(HttpServletRequest servletRequest, @RequestBody PublishActivityParam param){
        ActivityPO po = new ActivityPO();
        BeanUtils.copyProperties(param, po);
        po.setActivityStatus(ActivityStatusEnum.PUBLISHED);
        po.setSaleCount(0L);
        activityService.save(po);
        return ResultVO.ok();
    }

    @ApiOperation("上线一个秒杀活动(将任务加入消息队列)")
    @PostMapping("/online/{id}")
    @Auth
    public ResultVO online(HttpServletRequest servletRequest, @PathVariable("id") String activityId){
        ActivityPO activityPO = activityService.getById(activityId);
        activityPO.setActivityStatus(ActivityStatusEnum.ONLINE);
        // TODO 加入延时队列
        activityService.updateById(activityPO);
        redisTools.set(Constants.REDIS_ACTIVITY_PREFIX + activityId, JsonTools.toJSONString(activityPO));
        redisTools.hset(Constants.REDIS_STOCK_PREFIX + activityId, Constants.REDIS_STOCK_ALL_COUNT, activityPO.getStockCount().toString());
        redisTools.hset(Constants.REDIS_STOCK_PREFIX + activityId, Constants.REDIS_STOCK_SALE_COUNT, activityPO.getSaleCount().toString());
        return ResultVO.ok();
    }


    @ApiOperation("下线一个秒杀活动")
    @PostMapping("/offline/{id}")
    @Auth
    public ResultVO offline(HttpServletRequest servletRequest,@PathVariable("id") String activityId){
        // 移除定时任务
        String o = (String) redisTools.get(Constants.REDIS_ACTIVITY_PREFIX + activityId);
        ActivityPO po = JsonTools.parseJSONStr2T(o, ActivityPO.class);
        po.setActivityStatus(ActivityStatusEnum.OFFLINE);
        activityService.saveOrUpdate(po);
        redisTools.del(Constants.REDIS_ACTIVITY_PREFIX + activityId);
        return ResultVO.ok();
    }
}
