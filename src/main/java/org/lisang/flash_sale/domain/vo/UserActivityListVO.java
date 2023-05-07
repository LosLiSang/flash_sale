package org.lisang.flash_sale.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel
@Data
public class UserActivityListVO {

    @ApiModelProperty("ID")
    @TableId
    private String id;

    @ApiModelProperty("stock_count")
    @TableField("stock_count")
    private String stockCount;


    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;


    @ApiModelProperty("秒杀活动图片")
    @TableField("pic_url_list")
    private String picUrlList;

    @ApiModelProperty("秒杀价格")
    @TableField("flash_price")
    private BigDecimal flashPrice;

    @ApiModelProperty("原本价格")
    @TableField("origin_price")
    private BigDecimal originPrice;


    @ApiModelProperty("秒杀活动状态")
    @TableField("activity_status")
    private ActivityStatusEnum activityStatus;


    @ApiModelProperty("秒杀开始时间")
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
