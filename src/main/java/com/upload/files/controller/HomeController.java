package com.upload.files.controller;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/{id}")
    public String homeUserID(@Param("id")Long id, Model model) {
        model.addAttribute("id", id);
        return "home";
    }

    /**여행지, 계절, 테마 검색*/
    @RequestMapping(value="list")
    public String list(@ModelAttribute("listSearch") ListSearch listSearch, Model model) {
        List<Product> products = productService.findItemsByFilter(listSearch);
        model.addAttribute("items", products);
        return "board/list";
    }

    /** 지도 이동 */
    @GetMapping("board/map")
    public String map() {
        return "board/map";
    }

    @GetMapping("/header")
    public String header() {
        return "/layout/header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "/layout/footer";
    }

}