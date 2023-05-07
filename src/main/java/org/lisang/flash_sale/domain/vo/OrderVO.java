package org.lisang.flash_sale.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;
import org.lisang.flash_sale.domain.enums.UserRoleEnum;

@Data
public class OrderVO {


    @TableId
    private String id;

    @ApiModelProperty("支付状态")
    @TableField("pay_status")
    private OrderStatusEnum payStatus;

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
}
