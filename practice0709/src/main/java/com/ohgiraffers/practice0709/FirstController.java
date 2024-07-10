package com.ohgiraffers.practice0709;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/first/*")
//@SessionAttributes("id")
public class FirstController {

    @GetMapping("regist")
    public void regist(){
    }

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request){

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        System.out.println(name);
        System.out.println(price);
        System.out.println(categoryCode);

        String message = name+categoryCode+price;
        System.out.println(message);

        model.addAttribute("message", message);

        return "first.messagePrinter";
    }

    @GetMapping("modify")
    public void modify(){}

    @PostMapping("modify")
    public String modifyMenPrice(Model model, @RequestParam String modifyName,
                                 @RequestParam(defaultValue = "0")int modifyPrice){
        String message = modifyName + "메뉴가격을" + modifyPrice + "원으로 변경하였습니다";
        System.out.println(message);

        model.addAttribute("message", message);
        return "first/messagePrinter";
    }


}
