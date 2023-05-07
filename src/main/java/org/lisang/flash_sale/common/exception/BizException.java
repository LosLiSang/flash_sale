package org.lisang.flash_sale.common.exception;

import java.util.function.Supplier;

public class BizException extends RuntimeException{

    private Integer bizCode;

    public Integer getBizCode() {
        return bizCode;
    }

    public void setBizCode(Integer bizCode) {
        this.bizCode = bizCode;
    }

    public BizException() {
    }

    public BizException(Integer code ,String message) {
        super(message);
        this.bizCode = code;
    }

    public BizException(BizExceptionCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.bizCode = codeEnum.getCode();
    }
}
