package org.lisang.flash_sale.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.lisang.flash_sale.domain.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("activity")
@ApiModel(value = "ActivityPO对象", description = "")
public class ActivityPO extends BaseModel<ActivityPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("stock_id")
    @TableField("stock_id")
    private String stockId;

    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty("秒杀活动描述")
    @TableField("activity_desc")
    private String activityDesc;

    @ApiModelProperty("秒杀活动图片")
    @TableField("pic_url_list")
    private String picUrlList;

    @ApiModelProperty("秒杀价格")
    @TableField("flash_price")
    private BigDecimal flashPrice;

    @ApiModelProperty("原本价格")
    @TableField("origin_price")
    private BigDecimal originPrice;

    @ApiModelProperty("秒杀开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty("秒杀活动状态")
    @TableField("activity_status")
    private String activityStatus;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
