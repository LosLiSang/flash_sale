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
@TableName("sys_dict")
@ApiModel(value = "SysDictPO对象", description = "")
public class SysDictPO extends BaseModel<SysDictPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("字典描述")
    @TableField("desc")
    private String desc;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
