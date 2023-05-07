package org.lisang.flash_sale.domain.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.ActivityTimeStatusEnum;

import java.util.List;

@ApiModel("客户分页查询活动参数")
@Data
public class PageUserActivityParam {

    private List<ActivityTimeStatusEnum> statusList;

    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;


}
