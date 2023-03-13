package org.lisang.flash_sale.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-03-13
 */
@Getter
@Setter
@TableName("activity")
@ApiModel(value = "ActivityPO对象", description = "")
public class ActivityPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("stock_id")
    private String stockId;

    @ApiModelProperty("秒杀活动名称")
    private String activityName;

    @ApiModelProperty("秒杀活动描述")
    private String activityDesc;

    @ApiModelProperty("秒杀活动图片")
    private String picUrlList;

    @ApiModelProperty("是否删除")
    @TableLogic
    private String delFlag;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建者id")
    private String createUserId;

    @ApiModelProperty("更新者id")
    private String updateUserId;
}
