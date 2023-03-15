package org.lisang.flash_sale.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("account")
@ApiModel(value = "AccountPO对象", description = "")
public class AccountPO extends BaseModel<AccountPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
