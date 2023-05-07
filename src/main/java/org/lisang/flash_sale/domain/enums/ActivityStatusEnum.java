package org.lisang.flash_sale.domain.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;

public enum ActivityStatusEnum implements IEnum<String> {

    PUBLISHED("1", "已发布"),
    ONLINE("2", "已发布上线"),
    OFFLINE("3", "已下线"),
    NOT_STARTED("4", "待开始"),
    IN_PROGRESS("5", "正在秒杀"),
    ENDED("6", "已结束");



    @EnumValue
    private final String value;

    private final String label;

    ActivityStatusEnum(String value, String label){
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
