package org.lisang.flash_sale.controller.base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Ping模块")
@RestController
@RequestMapping("/test")
public class PingController {

    @ApiOperation("Ping接口")
    @GetMapping("/ping")
    public String ping(){
        return "pong!";
    }

}
