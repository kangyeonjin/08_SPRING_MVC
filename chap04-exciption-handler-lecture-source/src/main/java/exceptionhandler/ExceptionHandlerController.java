package exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("controller-null")
    public String nullPointerExceptionTest(){

        String str = null;
        System.out.println(str.charAt(0));
        return "/";

    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException exception){

        System.out.println("controller레벨의 exception처리");
        return "error/nullPointer";

    }

    @GetMapping("controller-user")
    public String userExceptionTest ()throws MemberRegistExciption {

        boolean check = true;
        if(check){
            throw new MemberRegistExciption("회원으로 받을수없습니다");
        }

        return "/";
    }

    @ExceptionHandler(MemberRegistExciption.class)
    public String userExceptionHandler(Model model, MemberRegistExciption exciption){

        System.out.println("메소드 레벨의 exception처리");
        model.addAttribute("exception", exciption);
        return "error/memberRegist";

    }

}
