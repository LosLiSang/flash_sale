package org.lisang.flash_sale.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lisang.flash_sale.domain.enums.UserRoleEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserInfoListVO {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户角色")
    @TableField("role")
    private UserRoleEnum role;

    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

}
