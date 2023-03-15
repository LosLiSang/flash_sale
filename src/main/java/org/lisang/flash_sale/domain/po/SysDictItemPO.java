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
@TableName("sys_dict_item")
@ApiModel(value = "SysDictItemPO对象", description = "")
public class SysDictItemPO extends BaseModel<SysDictItemPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典id")
    @TableField("dict_id")
    private String dictId;

    @ApiModelProperty("字典项名称")
    @TableField("item_name")
    private String itemName;

    @ApiModelProperty("字典项值")
    @TableField("item_value")
    private String itemValue;

    @ApiModelProperty("字典项描述")
    @TableField("item_desc")
    private String itemDesc;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
