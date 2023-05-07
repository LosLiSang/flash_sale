package org.lisang.flash_sale.domain.vo.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SmartSort {

    @ApiModelProperty(example = "")
    private String field;

    @ApiModelProperty(example = "")
    private boolean reverse;

}
