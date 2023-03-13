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
@TableName("stock")
@ApiModel(value = "StockPO对象", description = "")
public class StockPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("余量")
    private Long stockCount;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("图片名称列表（json序列化）")
    private String picIdList;

    @ApiModelProperty("商品描述")
    private String desc;

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
