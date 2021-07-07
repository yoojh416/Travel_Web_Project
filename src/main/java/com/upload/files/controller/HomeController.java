package com.upload.files.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }


    @GetMapping("default")
    public String Idefault(Model model) {
        model.addAttribute("data", "hello!!");
        return "layout/default";
    }


    @GetMapping("header")
    public String header(Model model) {
        model.addAttribute("data", "hello!!");
        return "/fragments/header";
    }

    @GetMapping("footer")
    public String footer(Model model) {
        model.addAttribute("data", "hello!!");
        return "/fragments/footer";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("data", "hello!!");
        return "home";
    }

    //유저(user)
//    @GetMapping(value = "account/login")
//    public String login(Model model) {
//        model.addAttribute("data", "login!!");
//        return "/account/login";
//    }

    @GetMapping(value = "user/logout")
    public String logout(Model model) {
        model.addAttribute("data", "logout!!");
        return "/user/logout";
    }

    @GetMapping(value = "user/reservation")
    public String reservation(Model model) {
        model.addAttribute("data", "reservation!!");
        return "/user/reservation";
    }

    @GetMapping(value = "user/userInfo")
    public String userInfo(Model model) {
        model.addAttribute("data", "userInfo!!");
        return "/user/userInfo";
    }

    /*관리자*/
    @GetMapping(value = "admin/upload")
    public String itemUpload(Model model) {
        model.addAttribute("data", "itemUpload!!");
        return "/admin/upload";
    }

    /*여행지, 계절, 테마 검색*/
    @GetMapping(value = "list")
    public String boardList(Model model) {
        model.addAttribute("data", "boardList!!");
        return "/board/list";
    }
    /*이미지 클릭 시 상세페이지 이동*/
    @GetMapping(value = "get")
    public String boardGet(Model model) {
        model.addAttribute("data", "boardGet!!");
        return "board/get";
    }
}