package com.upload.files.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("data", "hello!!");
        return "home";
    }

    @GetMapping("footer")
    public String footer(Model model) {
        model.addAttribute("data", "hello!!");
        return "/fragments/footer";
    }

}
