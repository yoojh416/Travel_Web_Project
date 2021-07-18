package com.upload.files.controller;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name; //회원 아이디

    private String password; //비밀번호
    private String useremail; //이메일
    private String address; //주소
    private String brithdate; //생일
    private String phoneno; //전화 번호

}