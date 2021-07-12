package com.upload.files.controller;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.repository.ProductRepository;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController { //여행 상품용 컨트롤러

    private final ProductService productService;
    //private final ProductRepository productRepository;

    @GetMapping(value = "/admin/new") //상품 등록
    public String register(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "admin/upload";
    }

    @PostMapping(value = "/admin/register") //입력한 상품 정보 DB에 저장
    public String registered(@Validated ProductForm form, BindingResult result, Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "admin/upload";
        }

        Product product = new Product(form.getProNo(), form.getProTitle(), form.getProWriter()
                , form.getProContent(), form.getRegion(), form.getSeason(), form.getTheme(), form.getPrice());
        productService.save(product);

        model.addAttribute("proNo", product.getProNo());

        return "/admin/fileUpload";
    }

    @GetMapping("/admin/modify/{proNo}") //상품 정보 수정 요청
    public String getProductUpdate(Model model, @PathVariable Long proNo) {
        //Product product = (Product) productRepository.findByProNo(proNo);
        Product product = productService.findOne(proNo);
        model.addAttribute("productForm", product);

        return "admin/modifyUpload";
    }

    @PostMapping(value = "/admin/modify/{proNo}") //상품 등록 후 상품 목록으로 이동
    public String setProductUpdate(Model model, @PathVariable Long proNo, Product updatedProduct){

        /*Product product = productService.findOne(proNo);
        product.setProNo(updatedProduct.getProNo());
        product.setProTitle(updatedProduct.getProTitle());
        product.setProWriter(updatedProduct.getProWriter());
        product.setProContent(updatedProduct.getProContent());
        product.setRegion(updatedProduct.getRegion());
        product.setSeason(updatedProduct.getSeason());
        product.setTheme(updatedProduct.getTheme());
        product.setPrice(updatedProduct.getPrice());
        productService.updateProduct();*/

        /*productService.updateProduct(updatedProduct, proNo);*/

        List<Product> productList = productService.products();
        model.addAttribute("products", productList);

        return "admin/modifyFileUpload";
    }

    @RequestMapping("/board/list") //등록 상품 목록
    public String list(@ModelAttribute("listSearch") ListSearch listSearch, Model model) {
        List<Product> products = productService.findItemsByFilter(listSearch);
        model.addAttribute("items", products);
        return "board/list";
    }

}