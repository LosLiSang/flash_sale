package org.lisang.flash_sale.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("user")
@ApiModel(value = "UserPO对象", description = "")
public class UserPO extends BaseModel<UserPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("密码（MD5加密）")
    @TableField("password")
    private String password;

    @ApiModelProperty("用户角色")
    @TableField("role")
    private String role;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
