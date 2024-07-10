package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")
public class FirstController {

    /*컨트롤러 핸들러 메서드의 반환값을 void로 설정하면 요청주소가 view의 이름이된다
    * ->first/regist요청이 들어오면  /first/regist뷰를 응답한다.*/

    //페이지 이동용 핸들러 메소드
    @GetMapping("regist")
    public void regist(){
    }

    /*webrequest로 요청 파라미터 전달받기
    * 파라미터 선언부에 webrequest타입을 선언하면 해당 메소드 호출시 인자로 값을 전달해준다
    * httpservletrequest, httpservletresponse/servletrequest, servletresponse*/

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request){

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        System.out.println("name = " + name);
        System.out.println("price = " + price);
        System.out.println("categoryCode = " + categoryCode);

        String message = name + "을 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록 하셨습니다";
        System.out.println(message);

        model.addAttribute("message", message);

        return  "first/messagePrinter";
    }

    /*@requestParm으로 요청 파라미터 전달받기
    * 요청 파라미터를 매핑해 호출시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다
    * from의 name속성값과 매개변수의 이름이 다른경우 @requestparm("이름")설정하면 된다
    * 생략이가능하다 하지만 가독성을 위해서 써주는게 좋다
    *
    * required속성 : 값이 들어오지않아도 400에러를 발생시키지 않는다(default =true)
    * defaultvalue :기본값을 설정
    * */

    @GetMapping("modify")
    public void modify(){}

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam String modifyName,
                                  @RequestParam (defaultValue = "0") int modifyPrice){
        String message = modifyName +"메뉴가격을"+ modifyPrice+"원으로 변경하였습니다.";
        System.out.println(message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /*파라미터가 여러개인경우 맵으로 한번에 처리할수있다
    * 이때 ,map의 키는 form의 name속성값이 된다
    * */

    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameter) {

        String modifyName = parameter.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameter.get("modifyPrice2"));

        String message = modifyName +"메뉴가격을"+ modifyPrice+"원으로 변경하였습니다.";
        System.out.println(message);

        model.addAttribute("message",message);
        return "first/messagePrinter";
    }

  //first/search
    @GetMapping("search")
    public void search(){
    }

    /*@ModelAttribute
    * DTO같은 모델을 커맨드 객체로 전달받는다
    * @modelAttribute의 경우 커맨드 객체를 생성하여 매개변수로 전달해준뒤 인스턴스를 model에 담느다
    * 경우에 따라 폼에서 입력한 값을 다음화면으로 바로 전달해야할 경우가 발생하는데,
    * 이때 유용하게 사용된다
    * @modelattribute(모델에 담을 key값)을 지정할수있으며
    * 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규칙을 따른다
    * menuDTO
    *
    * 해당 어노테이션은 생략이 가능하지만 명시적으로 작성하는것이 좋다
    * 이떄 주의할점은 dto와 입력받을 폼을 잘 맞춰줘야한다*/

    @PostMapping("search")
    public String searchMenu(@ModelAttribute MenuDTO menu){

        System.out.println(menu);
        return "first/messagePrinter";
    }

    @GetMapping("login")
    public void login(){
    }

    /*session이용하기
    * http session을 매개변수로 선언하면 핸들러메소드 호출시 세션객체를 넣어서 호출한다*/

    @PostMapping("login1")
    public String sessionTest1(HttpSession session, @RequestParam String id) {
        System.out.println(session);
        System.out.println(id);
        session.setAttribute("id",id);
        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session){
        session.invalidate();
        return "first/loginResult";
    }

    /*@SessionAttributes를 이용하여 session에 값 담기
    * 클래스 레벨에@sessionAttributes어노테이션을 이용하여 세션에 담을 key값을 설정
    * model영역에 해당 key로 값이 추가되는 경우 session에 자동으로 등록해준다*/

    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id) {

        model.addAttribute("id",id);
        return "first/loginResult";

    }
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus){
        //현재 컨트롤러 세션에 저장된 모든 정보를 제거
        //개별 제거가 불가능
        sessionStatus.setComplete();
        return "first/loginResult";

    }

    @GetMapping("body")
    public void body(){}

    /*@RequestBody
    * 해당어노테이션은 http본문 자체를 읽는 부분을 모델로 변환시켜주는 어노테이션*/

    @PostMapping("body")
    public void bodyTest(@RequestBody String body,
                         @RequestHeader("content-type")String contentType,
                         @CookieValue(value = "JSESSIONID",required = false)String sessionId) throws UnsupportedEncodingException {
        System.out.println(contentType);
        System.out.println(sessionId);
        System.out.println(body);
        System.out.println(URLDecoder.decode(body,"UTF-8"));

    }

}
