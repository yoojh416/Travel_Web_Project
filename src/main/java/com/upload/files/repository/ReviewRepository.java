package com.upload.files.repository;

import com.upload.files.entity.FilePath;
import com.upload.files.entity.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    FilePath findByReTitle(String reTitle);

}


