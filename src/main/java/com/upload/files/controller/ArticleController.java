package com.upload.files.controller;


import com.upload.files.entity.Article;
import com.upload.files.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Controller
public class ArticleController {

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
		log("setArticle");

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

//	/**글 한개 클릭*/
//	@GetMapping("/article/{id}")
//	public String getArticle(Model model, @PathVariable Long id) {
//		Article article = articleRepository.findById(id).get();
//		model.addAttribute("article", article);
//		return "article/detail";
//	}

	/**수정*/
	@GetMapping("/article/{id}")
	public String getArticle(@PathVariable("id") Long id, Model model) {
		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);
		List<Article> listarticles = articleRepository.findAll();
		model.addAttribute("listarticles", listarticles);

		return "article/detail";
	}	


	/*@GetMapping("/article/update/{id}")
	public String getArticleUpdate(Model model, @PathVariable Long id) {
		Article article = articleRepository.findById(id).get();
		System.out.println(article);
		model.addAttribute("article", article);
		return "article/update";
	}

	@PostMapping("/article/updated/{id}")
	public String setArticleUpdate(Model model, @PathVariable Long id, Article updatedArticle) {
		Article article = articleRepository.findById(id).get();
		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		article.setUpdateDate(LocalDateTime.now());
		articleRepository.save(article);
		return "redirect:/article/detail" + article.getId();
	}*/

}