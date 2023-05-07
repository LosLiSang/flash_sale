package org.lisang.flash_sale.domain.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;

@Data
public class UserOrderVO {

    private String id;

    @ApiModelProperty("支付状态")
    @TableField("pay_status")
    private OrderStatusEnum payStatus;


    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;


    @ApiModelProperty("订单创建时间")
    private String createTime;



}
