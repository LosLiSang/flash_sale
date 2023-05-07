package org.lisang.flash_sale.util;

import org.lisang.flash_sale.common.exception.BizExceptionCodeEnum;
import org.lisang.flash_sale.domain.vo.base.ResultVO;

import javax.servlet.ServletResponse;
import java.io.IOException;

public class ResponseExceptionHandelTools {

    public static void error(BizExceptionCodeEnum codeEnum, ServletResponse servletResponse) throws IOException {
        ResultVO resultVO = ResultVO.error(codeEnum.getCode(), codeEnum.getMsg());
        servletResponse.getWriter().print(JsonTools.toJSONString(resultVO));
        servletResponse.setContentType("application/json;charset=utf-8");
        servletResponse.setCharacterEncoding("utf-8");
    }

}
