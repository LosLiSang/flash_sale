package org.lisang.flash_sale.controller.base;


import org.lisang.flash_sale.common.exception.BizException;
import org.lisang.flash_sale.domain.vo.base.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(value = BizException.class)
    public  ResultVO handelBizException(HttpServletRequest req, BizException e){
        e.printStackTrace();
        return ResultVO.error(e.getBizCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResultVO handelException(HttpServletRequest req, Exception e){
        e.printStackTrace();
        return ResultVO.error(500, e.getMessage());
    }

}
