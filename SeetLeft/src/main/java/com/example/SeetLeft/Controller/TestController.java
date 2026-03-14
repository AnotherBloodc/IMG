package com.example.SeetLeft.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    // 根路径 "/" 重定向到你配置的 /Swagger.html
    @GetMapping("/")
    public String redirectToCustomSwagger() {
        // 重点：路径必须和你 yml 中配置的完全一致（区分大小写！）
        return "redirect:/Swagger.html";
    }
}
