package com.upload.files.repository;

import com.upload.files.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>{ //댓글 CRUD 용

}
