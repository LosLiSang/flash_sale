package org.lisang.flash_sale.domain.enums;

public enum OrderStatusEnum {

    ADMIN("1", "管理员"), CLIENT("2", "顾客"), RETAILER("3", "商家");


    private final String value;

    private final String label;

    OrderStatusEnum(String value, String label){
        this.value = value;
        this.label = label;
    }

    public String getCode() {
        return value;
    }

    public String getText() {
        return label;
    }


}
