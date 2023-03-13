package org.lisang.flash_sale.controller.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "pong!";
    }

}
