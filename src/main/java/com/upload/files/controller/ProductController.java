package com.upload.files.controller;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController { //여행 상품용 컨트롤러

    private final ProductService productService;

    @GetMapping(value = "/admin/new")
    public String register(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "admin/upload";
    }

    @PostMapping(value = "/admin/register")
    public String registered(@Validated ProductForm form, BindingResult result, Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "admin/upload";
        }

        Product product = new Product(form.getProNo(), form.getProTitle(), form.getProWriter()
                , form.getProContent(), form.getRegion(), form.getSeason(), form.getTheme(), form.getPrice());
        productService.save(product);

        model.addAttribute("proNo", product.getProNo());

        /*HttpSession session = request.getSession();
        session.setAttribute("proNo", product.getProNo());*/

        return "/admin/fileUpload";
    }

    @RequestMapping(value = "/board/list")
    public String list(@ModelAttribute("listSearch") ListSearch listSearch, Model model){
        List<Product> products = productService.findItemsByFilter(listSearch);
        model.addAttribute("items", products);
        return "board/list";
    }

}