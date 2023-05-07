package org.lisang.flash_sale.domain.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;


@Data
@ApiModel
public class SmartPagination implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("从第几页开始")
    private Integer current;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

    @ApiModelProperty("结果页数")
    private Integer pages;

    @ApiModelProperty("查询结果总数")
    private Integer total;


    @Override
    public String toString() {
        return new StringJoiner(", ", SmartPagination.class.getSimpleName() + "[", "]")
                .add("current=" + current)
                .add("pageSize=" + pageSize)
                .add("numberOfPages=" + pages)
                .add("totalItemCount=" + total)
                .toString();
    }
}