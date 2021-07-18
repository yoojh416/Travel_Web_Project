package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id; //회원 고유 번호(조회 할때 사용)

    private String name; //회원 아이디
    private String password; //비밀번호
    private String useremail; //이메일
    private String address; //주소
    private String brithdate; //생일
    private String phoneno; //전화 번호


   /* @OneToMany(mappedBy = "member") //주문조회
    private List<Order> orders = new ArrayList<>();*/
}