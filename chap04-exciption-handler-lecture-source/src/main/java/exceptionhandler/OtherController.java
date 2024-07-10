package exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("other-controller-null")
    public String othernullPointerExceptionTest(){

        String str = null;
        System.out.println(str.charAt(0));

        return "/";

    }

    @GetMapping("other-controller-user")
    public String otherUserExceptionTest() throws MemberRegistExciption{

        boolean check =true;

        if(check){
            throw new MemberRegistExciption("회원으로 받을수없습니다");
        }
        return "error/nullPointer";

    }


}
