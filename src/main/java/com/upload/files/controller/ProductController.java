package com.upload.files.controller;

import com.upload.files.entity.*;
import com.upload.files.repository.ArticleRepository;
import com.upload.files.repository.FilePathRepository;
import com.upload.files.repository.MemberRepository;
import com.upload.files.repository.ProductRepository;
import com.upload.files.service.ArticleService;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController { //여행 상품용 컨트롤러

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FilePathRepository filePathRepository;
    private final ArticleService articleService;
    private final MemberRepository memberRepository;

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
    public String registered(@Validated ProductForm form, @AuthenticationPrincipal UserDetails userDetails,
                             BindingResult result, Model model) {

        if (result.hasErrors()) { // 업로드 오류 -> 재 입력
            return "admin/upload";
        }

        Product product = new Product(form.getProNo(), form.getProTitle(), userDetails.getUsername()
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
        int[] fno = filePathRepository.findAllFno(proNo);

        /* 이미지 먼저 삭제 */
        for (int i = 0; i < fno.length; ++i) {
            filePathRepository.deleteById(fno[i]);
        }

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
    public String list(@ModelAttribute("listSearch") ListSearch listSearch,
                       @PageableDefault Pageable pageable,
                       @RequestParam(value = "page", defaultValue = "0") String pageNum,
                       Model model) {
        List<Product> products = productService.findByFilter(listSearch);
        Page<Product> pagingProducts = productService.pagingFindItemsByFilter(listSearch, pageable);
        List<FilePath> files = new ArrayList<>();
        //리서치 서비스 페이징 추가 요망

        for (int i = 0; i < products.size(); i++) {
            Long proNo = products.get(i).getProNo();
            int[] fno = filePathRepository.findAllFno(proNo);
            Optional<FilePath> MainImage = filePathRepository.findById(fno[0]);
            files.add(MainImage.get());

            model.addAttribute("items", pagingProducts);
            model.addAttribute("MainImage", files);

        }

        return "board/list";
    }

    @GetMapping("/board/get")
    public String getProduct(@RequestParam(name = "proNo") Long proNo,
                             @RequestParam(value = "page", defaultValue = "0") String pageNum,
                             @PageableDefault Pageable pageable, Model model) {

        Product product = productService.findOne(proNo);
        model.addAttribute("productInfo", product);

        int[] fno = filePathRepository.findAllFno(proNo);
        FilePath MainImg = filePathRepository.findById(fno[0]).get();
        FilePath DetailImg = filePathRepository.findById(fno[1]).get();

        model.addAttribute("MainImg", MainImg.getFileName());
        model.addAttribute("DetailImg", DetailImg.getFileName());

        Page<Article> articleList = articleService.getArticleList(pageable);
        model.addAttribute("articleList", articleList);

        return "board/get";
    }

}