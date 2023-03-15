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
@TableName("sys_file")
@ApiModel(value = "SysFilePO对象", description = "")
public class SysFilePO extends BaseModel<SysFilePO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件路径")
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty("文件名称")
    @TableField("file_name")
    private String fileName;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
