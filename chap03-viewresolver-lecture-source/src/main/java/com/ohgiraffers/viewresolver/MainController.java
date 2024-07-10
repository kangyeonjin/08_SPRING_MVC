package com.ohgiraffers.viewresolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value ={ "/","/main"})
    public String main(){return "main";}

}
