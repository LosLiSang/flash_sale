package org.lisang.flash_sale.domain.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.ActivityStatusEnum;
import org.lisang.flash_sale.domain.enums.ActivityTimeStatusEnum;

import java.util.List;

@Data
public class PageActivityParam {

    private List<ActivityStatusEnum> statusList;

    @ApiModelProperty("秒杀活动名称")
    @TableField("activity_name")
    private String activityName;

}
