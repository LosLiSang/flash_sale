package org.lisang.flash_sale.domain.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.enums.UserRoleEnum;

import java.math.BigDecimal;

@Data
public class OrderUserActivityDTO {

    @TableId
    private String id;

    @ApiModelProperty("支付状态")
    @TableField("pay_status")
    private OrderStatusEnum payStatus;

    @ApiModelProperty("秒杀活动描述")
    @TableField("activity_desc")
    private String activityDesc;


    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;


    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty("秒杀活动状态")
    @TableField("activity_status")
    private ActivityStatusEnum activityStatus;


    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户角色")
    @TableField("role")
    private UserRoleEnum role;

    @ApiModelProperty("秒杀价格")
    @TableField("flash_price")
    private BigDecimal flashPrice;

    @ApiModelProperty("原本价格")
    @TableField("origin_price")
    private BigDecimal originPrice;

}
