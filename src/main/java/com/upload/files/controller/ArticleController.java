package com.upload.files.controller;


import com.upload.files.entity.Article;
import com.upload.files.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ArticleController { //리뷰용 컨트롤러

	@Autowired
	private ArticleRepository articleRepository;

	/**폼으로 가기*/
	@GetMapping("/form")
	public String form() {
		return "article/write";
	}

	/**글쓰기*/
	@PostMapping("/write")
	public String setArticle(Article article, Model model) {
		article.setRegisterDate(LocalDateTime.now());
		articleRepository.save(article);

		return "redirect:/article/list";
	}

	/**전체 리스트 */
	@GetMapping("/article/list")
	public String getArticleList(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);
		articleList.forEach(System.out::println);

		return "article/list";
	}

	/**상세 페이지*/
	@GetMapping("/article/{id}")
	public String getArticle(@PathVariable("id") Long id, Model model) {
		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		List<Article> listArticles = articleRepository.findAll();
		model.addAttribute("listArticles", listArticles);

		return "article/detail";
	}

	/**수정 페이지 들어가기*/
	@GetMapping("/article/update/{id}")
	public String getArticleUpdate(Model model, @PathVariable Long id) {
		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		return "article/update";
	}

	/**수정 하기*/
	@PostMapping(value = "/article/updated/{id}")
	public String setArticleUpdate(Model model, @PathVariable Long id, Article updatedArticle) {
		Article article = articleRepository.findById(id).get();
		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		article.setUpdateDate(LocalDateTime.now());
		articleRepository.save(article);

		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);

		return "article/list";
	}

	/** 삭제하기*/
	@GetMapping("/article/delete/{id}")
	@Transactional
	public String deleteArticle(Model model, @PathVariable Long id) {
		articleRepository.deleteById(id);

		return "redirect:/article/list";
	}

}