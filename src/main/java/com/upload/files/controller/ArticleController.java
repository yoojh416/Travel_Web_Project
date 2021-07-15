package com.upload.files.controller;


import com.upload.files.entity.Article;
import com.upload.files.repository.ArticleRepository;
import com.upload.files.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ArticleController { //리뷰용 컨트롤러

	@Autowired private ArticleRepository articleRepository;
	@Autowired private ArticleService articleService;

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

	/** 전체 리스트 + 페이징 */
	@GetMapping("/article/list")
	public String getArticleList(Model model, @PageableDefault Pageable pageable
			, @RequestParam(value = "page", defaultValue = "1") String pageNum) {
		Page<Article> articleList = articleService.getArticleList(pageable);
		model.addAttribute("articleList", articleList);

		return "article/list";
	}

	/** 검색기능 추가 */
	@GetMapping("/article/search")
	public String getSearch(Model model, @PageableDefault Pageable pageable
			, @RequestParam(value = "page", defaultValue = "1") String pageNum
			, String keyword) {
		Page result;
		Page<Article> articleList = articleService.getArticleList(pageable);
		Page<Article> searchList = articleService.search(keyword, pageable);

		/*검색어가 없으면 전체 리스트 반환하는 로직*/
		if(keyword.isEmpty()) {
			result = articleList;
		} else {
			result = searchList;
		}

		model.addAttribute("articleList", result);

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

		return "redirect:/article/{id}";
	}

	/** 삭제하기*/
	@GetMapping("/article/delete/{id}")
	@Transactional
	public String deleteArticle(Model model, @PathVariable Long id) {
		articleRepository.deleteById(id);

		return "redirect:/article/list";
	}

}