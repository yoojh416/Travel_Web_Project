package com.upload.files.controller;

import com.upload.files.entity.Member;
import com.upload.files.repository.MemberDto;
import com.upload.files.repository.MemberRepository;
import com.upload.files.repository.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@Controller
@AllArgsConstructor
public class MemberController {

    @Autowired private MemberRepository memberRepository;

    // 회원가입 페이지
    @GetMapping("/user/join")
    public String dispSignup(@ModelAttribute(name = "memberDto") @Valid MemberDto memberDto
            , BindingResult result, Model model) {

        model.addAttribute("memberDro", memberDto);
        return "user/join";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(@ModelAttribute(name = "memberDto") @Valid MemberDto memberDto
            , BindingResult result, Model model, Member member) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());
        member.setBirthdate(memberDto.getBirthdate());
        member.setGender(memberDto.getGender());
        member.setPhoneNo(memberDto.getPhoneNo());
        member.setRole(Role.MEMBER); //어드민 1계정, 나머지 고객
        member.setUsername(memberDto.getUsername());
        member.setRegDate(LocalDate.now());
        this.memberRepository.save(member);
        model.addAttribute("member", member);
        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "user/login";
    }

    //로그인 기능 구현
    /*@PostMapping("/signIn")
    public String signIn(@Param("username") String username, @Param("password") String password
            , Model model) {
        log.info("id : {} , pw : {}", username, password);
        Member member = this.memberRepository.findMember(username, password);
        if(member != null) {
            model.addAttribute("id", member.getId());
            return "redirect:/";
        } else {
            return "user/login";
        }
    }*/

    // 로그아웃 메인 페이지로 이동
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "user/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info/{id}")
    public String dispMyInfo(@Param("id")Long id, Member member, Model model) {
        member = memberRepository.findById(id).get();

        model.addAttribute("members", member);
        return "user/myInfo";
    }

    // 정보 수정 페이지
    @GetMapping("/user/modify/{id}")
    public String modifyMyInfo(@PathVariable("id") Long id, @Valid MemberDto memberDto
            , BindingResult result, Model model, Member member) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());
        member.setBirthdate(memberDto.getBirthdate());
        member.setGender(memberDto.getGender());
        member.setPhoneNo(memberDto.getPhoneNo());
        member.setRole(Role.MEMBER); //어드민 1계정, 나머지 고객
        member.setUsername(memberDto.getUsername());
        member.setRegDate(LocalDate.now());
        this.memberRepository.save(member);
        model.addAttribute("member", member);
        return "redirect:/user/login";
    }

    // 계정삭제 로직 -> 삭제 후 홈으로 이동
    @GetMapping("/user/signOut")
    public String deleteMyInfo(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);
        return "/";
    }

    // 어드민 페이지
    @GetMapping("/admin/userList")
    public String dispAdmin(Member member, Model model) {
        List<Member> memberList = memberRepository.findAll();

        model.addAttribute("members", memberList);

        return "admin/memberList";
    }

}
