package com.upload.files.controller;

import com.upload.files.entity.Article;
import com.upload.files.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/article")
public class FileUploadController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/write")
    public String setArticle(Article article, Model model) {
        article.setRegisterDate(LocalDateTime.now());
        System.out.println(article);
        articleRepository.save(article);
        return "redirect:/article/" + article.getId();
    }

    @GetMapping("/write/{id}")
    public String getArticle(Model model, @PathVariable Long id) {
        Article article = articleRepository.findById(id).get();
        model.addAttribute("article", article);
        return "article/detail";
    }

    @GetMapping("/write")
    public String getArticleList(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        articleList.forEach(System.out::println);
        return "article/list";
    }

    @GetMapping("/update/{id}")
    public String getArticleUpdate(Model model, @PathVariable Long id) {
        Article article = articleRepository.findById(id).get();
        System.out.println(article);
        model.addAttribute("article", article);
        return "article/update";
    }

    @PostMapping("/update/{id}")
    public String setArticleUpdate(Model model, @PathVariable Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id).get();
        article.setTitle(updatedArticle.getTitle());
        article.setContent(updatedArticle.getContent());
        article.setUpdateDate(LocalDateTime.now());
        articleRepository.save(article);
        return "redirect:/article/" + article.getId();

    }

}
