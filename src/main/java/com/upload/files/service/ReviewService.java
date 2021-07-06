package com.upload.files.service;

import com.upload.files.entity.Review;
import com.upload.files.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void save(Review review) {
        Review re = new Review();
        re.setReTitle(review.getReTitle());
        re.setReWriter(review.getReWriter());
        re.setReContent(review.getReContent());

        reviewRepository.save(re);
    }

}
