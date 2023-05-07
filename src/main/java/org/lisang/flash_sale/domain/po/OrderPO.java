package org.lisang.flash_sale.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.ToString;
import org.lisang.flash_sale.domain.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.lisang.flash_sale.domain.enums.OrderStatusEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author lisang
 * @since 2023-03-14
 */
@Getter
@Setter
@TableName("`order`")
@ToString
@ApiModel(value = "OrderPO对象", description = "")
public class OrderPO extends BaseModel<OrderPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("秒杀活动id")
    @TableField("activity_id")
    private String activityId;

    @ApiModelProperty("支付状态")
    @TableField("pay_status")
    private OrderStatusEnum payStatus;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
