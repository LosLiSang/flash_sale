package org.lisang.flash_sale.common.exception;


public enum BizExceptionCodeEnum {
    TOKEN_EXPIRE_EXCEPTION(501, "Token过期"),

    TOKEN_NEEDED_EXCEPTION(502, "缺少Token"),
    TOKEN_ERROR_EXCEPTION(503, "Token有误"),

    LOGIN_ACCOUNT_NOT_EXIST(511, "账户不存在"),
    LOGIN_PASSWORD_ERROR(512, "密码错误"),

    STOCK_MISS(521, "库存不足"),
    ORDER_STATUS_ALREADY_CANCELED(522, "订单已取消"),
    ORDER_STATUS_ALREADY_PAYED(523, "订单已支付"),
    ORDER_STATUS_ERROR(524, "订单状态错误"),
    ACCOUNT_BALANCE_LOW(531, "余额不足"),

    PARAM_EXCEPTION(540, "参数错误");

    private Integer code;
    private String msg;

    BizExceptionCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
