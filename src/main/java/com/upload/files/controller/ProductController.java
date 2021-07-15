package com.upload.files.controller;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.repository.ArticleRepository;
import com.upload.files.repository.FilePathRepository;
import com.upload.files.repository.ProductRepository;
import com.upload.files.service.ArticleService;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController { //여행 상품용 컨트롤러

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FilePathRepository filePathRepository;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    /**
     * 상품등록 페이지로 이동
     */
    @GetMapping("/admin/new")
    public String register(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "admin/upload";
    }

    /**
     * 입력한 상품 정보 저장
     */
    @PostMapping("/admin/register")
    public String registered(@Validated ProductForm form, BindingResult result
            , Model model, HttpServletRequest request) {

        if (result.hasErrors()) { // 업로드 오류 -> 재 입력
            return "admin/upload";
        }

        Product product = new Product(form.getProNo(), form.getProTitle(), form.getProWriter()
                , form.getProContent(), form.getRegion(), form.getSeason(), form.getTheme()
                , form.getPrice(), form.getN(), form.getE());

        productService.save(product);

        model.addAttribute("proNo", product.getProNo());

        return "/admin/fileUpload";
    }

    /**
     * 상품 정보 수정 요청
     */
    @GetMapping("/admin/modify/{proNo}")
    public String getProductUpdate(Model model, @PathVariable Long proNo) {
        Product product = productRepository.findOne(proNo);
        model.addAttribute("productForm", product);

        return "admin/modifyUpload";
    }

    /**
     * 상품 등록 후 상품 목록으로 이동
     */
    @Transactional
    @PostMapping("/admin/modified/{proNo}")
    public String setProductUpdate(Model model, @PathVariable Long proNo, Product updatedProduct) {
        Product product = productService.findOne(proNo);

        product.setProTitle(updatedProduct.getProTitle());
        product.setProContent(updatedProduct.getProContent());
        product.setRegion(updatedProduct.getRegion());
        product.setSeason(updatedProduct.getSeason());
        product.setTheme(updatedProduct.getTheme());
        product.setPrice(updatedProduct.getPrice());
        product.setE(updatedProduct.getE());
        product.setN(updatedProduct.getN());

        productRepository.save(product);

        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);

        return "redirect:/admin/fileUpdate/{proNo}";
    }

    /**
     * 삭제하기
     */
    @GetMapping("/admin/delete/{proNo}")
    @Transactional
    public String deleteProduct(Model model, @PathVariable Long proNo, Product product) {

        /* 이미지 먼저 삭제 */
        int fno = filePathRepository.findFno(proNo);
        filePathRepository.deleteById(fno);

        /* 상품 텍스트 삭제 */
        productRepository.deleteProduct(product);

        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);

        return "redirect:/admin/list";
    }

    /**
     * 메인 상품 페이지
     */
    @RequestMapping("/board/list")
    public String list(@ModelAttribute("listSearch") ListSearch listSearch, Model model) {
        List<Product> products = productService.findItemsByFilter(listSearch);
        model.addAttribute("items", products);

        return "board/list";
    }

}