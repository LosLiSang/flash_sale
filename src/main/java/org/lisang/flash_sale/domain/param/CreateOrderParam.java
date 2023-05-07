package org.lisang.flash_sale.domain.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;

@Data
@ApiModel("下单参数")
public class CreateOrderParam {

//    @ApiModelProperty(value = "用户id", example = "1")
//    @TableField("user_id")
//    private String userId;

    @ApiModelProperty(value = "秒杀活动id", example = "1")
    @TableField("activity_id")
    private String activityId;

//    @ApiModelProperty(value = "支付状态", example = "1")
//    @TableField("pay_state")
//    private OrderStatusEnum payState;

}
