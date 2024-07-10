package exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

//전역 예외처리 담당
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException exception){

        System.out.println("Global레벨의 exception처리");
        return "error/nullPointer";

    }

    @ExceptionHandler(MemberRegistExciption.class)
    public String userExceptionHandler(Model model, MemberRegistExciption exception){

        System.out.println("Global레벨의 exception처리");
        model.addAttribute("exception", exception);
        return "error/memberRegist";

    }

    /*최상위 타입인 Exception을 이용하면 구체적으로 작성하지 않은 타입의 에러가 발생해도
    *처리가 가능하므로 default처리 용도로 사용할수있다.*/

    @ExceptionHandler(Exception.class)
    public String efaultExceptionHandler(Exception exception){
        return "error/default";
    }
    @GetMapping("other-controller-array")
    public String otherArrayExceptionTest(){
        double[] array = new double[1];
        System.out.println(array[3]);
        return "/";
    }

}
