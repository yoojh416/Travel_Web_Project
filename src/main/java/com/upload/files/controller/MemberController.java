package com.upload.files.controller;

import com.upload.files.entity.Member;
import com.upload.files.repository.MailDto;
import com.upload.files.repository.MemberDto;
import com.upload.files.repository.MemberRepository;
import com.upload.files.repository.Role;
import com.upload.files.service.MemberService;
import com.upload.files.service.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;


@Slf4j
@Controller
@AllArgsConstructor
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 페이지
     */
    @GetMapping("/user/join/{username}")
    public String dispSignup(@ModelAttribute(name = "memberDto") @Valid MemberDto memberDto
            , @PathVariable("username") String username, BindingResult result, Model model) {
        memberDto.setUsername(username);

        model.addAttribute("memberDto", memberDto);
        return "user/join";
    }

    @GetMapping("/user/join")
    public String dispSignup(@ModelAttribute(name = "memberDto") @Valid MemberDto memberDto
            , BindingResult result, Model model) {

        model.addAttribute("memberDto", memberDto);
        return "user/join";
    }

    /**
     * 회원가입 처리
     */
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

    /**
     * 가입시, 중복 아이디 확인 메서드
     */
    @GetMapping("/exists/{username}")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@PathVariable String username) {
        return ResponseEntity.ok(memberService.checkUsernameDuplicate(username));
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/user/login")
    public String dispLogin() {
        return "user/login";
    }

    /**
     * 로그아웃 메인 페이지로 이동
     */
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/";
    }

    /**
     * 권한 거부 페이지 -> 추후 알람으로 대체
     */
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "user/denied";
    }

    /**
     * 내 정보 페이지
     */
    @GetMapping("/user/info")
    public String dispMyInfo(@AuthenticationPrincipal UserDetails userDetails
            , Member member, Model model) {
        member = memberRepository.findByUsername(userDetails.getUsername());

        model.addAttribute("members", member);
        return "user/myInfo";
    }

    /**
     * 수정 전 비밀번호 확인
     */
    @GetMapping("/user/passwordCheck")
    public String CheckPw() {
        return "user/passwordCheck";
    }

    @PostMapping("/checkingPw")
    public String checkingPw(@AuthenticationPrincipal UserDetails userDetails, Model model
            , @Nullable Member member, HttpServletRequest request) {

        member = memberRepository.findByUsername(userDetails.getUsername());
        String dbPw = member.getPassword();
        String inputPw = request.getParameter("password");

        if (bCryptPasswordEncoder.matches(inputPw, dbPw)) {
            model.addAttribute("member", member);
            return "user/modifyMyInfo";
        } else {
            return "user/passwordCheck";
        }
    }

    /**
     * 정보 수정 로직
     */
    @PostMapping("/user/modified")
    public String modifyMyInfo(@AuthenticationPrincipal UserDetails userDetails
            , @Valid MemberDto memberDto, BindingResult result, Model model, Member member) {

        memberRepository.deleteById(memberDto.getId());

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

        return "redirect:/user/login";
    }

    /**
     * 계정삭제 로직 -> 삭제 후 login 으로 이동
     */
    @GetMapping("/user/delete/{id}")
    public String deleteMyInfo(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);

        return "redirect:/user/login";
    }

    /**
     * 어드민 페이지
     */
    @GetMapping("/admin/userList")
    public String dispAdmin(Member member, Model model) {
        List<Member> memberList = memberRepository.findAll();

        model.addAttribute("members", memberList);

        return "admin/memberList";
    }

    /**
     * 비밀번호 찾기
     */
    @GetMapping("/findInfo")
    public String findInfo(Member member, BindingResult result, Model model) {

        model.addAttribute("member", member);
        return "user/findInfo";
    }

    /**
     * 아이디 (이메일) 찾기
     */
    @GetMapping("/findId")
    public String findId() {
        return "user/findEmail";
    }

    @PostMapping("/user/findId")
    public String findingId(HttpServletRequest request, Model model) throws Exception {
        String phoneNo = request.getParameter("phoneNo");
        String name = request.getParameter("name");

        if (memberService.findEmail(phoneNo, name)) {
            Member member = memberRepository.findMember(phoneNo, name);
            model.addAttribute("member", member);

            return "user/findResult";
        } else {
            return "user/findEmail";
        }
    }

    /**
     * 비밀번호 찾기할때 이메일과 이름이 있는지 조회
     */
    @GetMapping("/check/findPw")
    public @ResponseBody
    Map<String, Boolean> pw_find(String username, String name) {
        Map<String, Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.userEmailCheck(username, name);

        json.put("data", pwFindCheck);

        return json;
    }

    /**
     * 임시 비밀번호 보내기
     */
    @PostMapping("/mail")
    public String sendPwMail(@RequestBody String username, @RequestBody String name
            , @ModelAttribute("member") MemberDto memberDto
            , MailDto mailDto, BindingResult result, Model model) {

        if (sendEmailService.createMailAndChangePassword(memberDto.getUsername(), memberDto.getName()) != null) {
            mailDto = sendEmailService.createMailAndChangePassword(memberDto.getUsername(), memberDto.getName());
            sendEmailService.mailSend(mailDto);

            return "user/login";
        } else {
            return "user/findInfo";
        }
    }

    /**
     * 이메일 인증 폼으로 가기
     */
    @GetMapping("/verifyEmail")
    public String verifyEmail(@ModelAttribute(name = "memberDto") @Valid MemberDto memberDto
            , BindingResult result, Model model) {
        return "user/verifyEmail";
    }

    @PostMapping("/verifying")
    public String verifying(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");

        MailDto mailDto = sendEmailService.verifyUserAccount(username);
        sendEmailService.mailSend(mailDto);

        model.addAttribute("username", username);

        return "user/login";
    }

}