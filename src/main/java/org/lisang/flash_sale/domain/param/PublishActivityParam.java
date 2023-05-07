package org.lisang.flash_sale.domain.param;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel("发布活动参数模型")
@Data
public class PublishActivityParam {

    @ApiModelProperty(value = "stock_count", example = "1000")
    @TableField("stock_count")
    private Long stockCount;

    @ApiModelProperty(value = "秒杀活动名称", example = "秒杀活动1")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty(value = "秒杀活动描述", example = "这里是描述")
    @TableField("activity_desc")
    private String activityDesc;

//    @ApiModelProperty("秒杀活动图片")
//    @TableField("pic_url_list")
//    private String picUrlList;

    @ApiModelProperty(value = "秒杀价格", example = "1")
    @TableField("flash_price")
    private BigDecimal flashPrice;

    @ApiModelProperty(value = "原本价格", example = "1")
    @TableField("origin_price")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "秒杀开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "秒杀活动状态", example = "1")
    @TableField("activity_status")
    private String activityStatus;

}
