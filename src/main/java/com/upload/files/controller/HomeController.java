package com.upload.files.controller;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SpringBootApplication(scanBasePackages = {"com.upload.files"})
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

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

    /**여행지, 계절, 테마 검색*/
    @RequestMapping(value="list")
    public String list(@ModelAttribute("listSearch") ListSearch listSearch, Model model) {
        List<Product> products = productService.findItemsByFilter(listSearch);
        model.addAttribute("listSearch", products);
        return "board/filteredList";
    }

    /** 지도 이동 */
    @GetMapping("board/map")
    public String map() {
        return "board/map";
    }

}