package org.lisang.flash_sale.domain.enums;


import com.baomidou.mybatisplus.annotation.IEnum;

public enum ActivityTimeStatusEnum implements IEnum<String> {
    NOT_STARTED("1", "待开始"),
    IN_PROGRESS("2", "正在秒杀"),
    ENDED("3", "已结束");

    private final String value;

    private final String label;

    ActivityTimeStatusEnum(String value, String label){
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

}
