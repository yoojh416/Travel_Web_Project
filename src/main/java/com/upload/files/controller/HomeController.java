package com.upload.files.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringBootApplication(scanBasePackages = {"com.upload.files"})
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("data", "hello!!");
        return "home";
    }

    //유저(user)
    @GetMapping(value = "user/login")
    public String login(Model model) {
        model.addAttribute("data", "login!!");
        return "/user/login";
    }

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

    /*여행지, 계절, 테마 검색*/
    @RequestMapping("list")
    public String boardList(Model model) {
        //model.addAttribute("data", "boardList!!");
        return "/board/list";
    }

}