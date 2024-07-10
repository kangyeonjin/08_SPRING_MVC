package com.ohgiraffers.handlermethod;

import lombok.*;

/*
* DTO를 커맨드 객체로 이용하기 위해서는 form에 있는 name과 값이 일치하게 만들어야한다*/
@NoArgsConstructor  //커맨드 객체는 기본생성자를 이용해서 인스턴스를 만들기때문에 기본생성자 반드시 필요
@AllArgsConstructor
@Getter
@Setter //요청 파라미터 NAME과 일치하는 필드의 SETTER를 이용하기 떄문에 네이밍룰에 맞는 SETTER메소드가 작성되어야 한다.
@ToString
public class MenuDTO {

    private String name;
    private int price;
    private int categoryCode;
    private String orderableStatus;


}
