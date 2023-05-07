package org.lisang.flash_sale.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatusEnum {

    CREATED("1", "待支付"),
    CANCELED("2", "已取消"),
    PAYED("3", "已支付"),
    TIMEOUT("4", "支付超时");

    @EnumValue
    private final String value;

    private final String label;

    OrderStatusEnum(String value, String label){
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }


}
