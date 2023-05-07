package org.lisang.flash_sale.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;

public enum UserRoleEnum implements IEnum<String> {

    ADMIN("1", "管理员"),
    CLIENT("2", "顾客"),
    RETAILER("3", "商家");

    @EnumValue
    private final String value;

    private final String label;

    UserRoleEnum(String value, String label) {
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
