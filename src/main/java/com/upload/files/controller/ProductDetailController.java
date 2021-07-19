/*
package com.upload.files.controller;

import com.upload.files.entity.Article;
import com.upload.files.entity.FilePath;
import com.upload.files.entity.Product;
import com.upload.files.repository.FilePathRepository;
import com.upload.files.service.ArticleService;
import com.upload.files.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductService productService;
    private final FilePathRepository filePathRepository;
    private final ArticleService articleService;

    @GetMapping("/board/get")
    public String getProduct(@RequestParam(name = "proNo") Long proNo, Model model) {
    */
/*public String getProduct(@RequestParam(name = "proNo") Long proNo,
                             @RequestParam(value="page", defaultValue = "1") String pageNum,
                             @PageableDefault Pageable pageable, Model model) {*//*


        Product product = productService.findOne(proNo);
        model.addAttribute("productInfo", product);

        int[] fno = filePathRepository.findAllFno(proNo);
        model.addAttribute("images", fno);

        */
/*int fno = filePathRepository.findFno(proNo);
        FilePath file = filePathRepository.findById(fno).get();
        model.addAttribute("fileInfo", file);*//*


        */
/*Page<Article> articleList = articleService.getArticleList(pageable);*//*

        */
/*model.addAttribute("articleList", articleList);*//*


        return "board/get";
    }
}
*/
