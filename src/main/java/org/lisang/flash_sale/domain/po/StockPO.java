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
@TableName("stock")
@ApiModel(value = "StockPO对象", description = "")
public class StockPO extends BaseModel<StockPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("余量")
    @TableField("stock_count")
    private Long stockCount;

    @ApiModelProperty("商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("图片名称列表（json序列化）")
    @TableField("pic_id_list")
    private String picIdList;

    @ApiModelProperty("商品描述")
    @TableField("`desc`")
    private String desc;

    @Override
    public Serializable pkVal() {
        return null;
    }
}
