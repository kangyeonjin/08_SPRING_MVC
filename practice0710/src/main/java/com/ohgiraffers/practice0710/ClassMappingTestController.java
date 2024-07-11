package com.ohgiraffers.practice0710;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {

    @GetMapping("regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "Get방식의 주문등록용 핸들러 메소드");
        return "page/mappingResult";
    }

}
