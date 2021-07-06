package com.upload.files.controller;

import com.upload.files.entity.Review;
import com.upload.files.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @RequestMapping("/board/reviewForm")
    public String reviewForm() {
        return "/board/review";
    }

    @RequestMapping("/board/writeReview")
    public String processForm(@ModelAttribute Review review, BindingResult result, Model model) {
        model.addAttribute("student", review);
        return "/board/get";
    }
}