package org.lisang.flash_sale.domain.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class SmartPageVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private SmartPagination pagination;

    private T searchPO;

    private SmartSort sort;

    private String type;


    @Override
    public String toString() {
        // Create a copy, don't share the array
        return this.getPagination().toString();
    }


}
