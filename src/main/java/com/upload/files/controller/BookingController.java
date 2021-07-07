package com.upload.files.controller;

import com.upload.files.entity.Booking;
import com.upload.files.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/book")
    public String main(Model model) {
        model.addAttribute("order", new Booking());
        return "order/booking";
    }

    @PostMapping("/confirm")
    public String save(Booking booking, Model model) {
        bookingService.save(booking);
        model.addAttribute("order", booking);
        return "order/confirmation";
    }
}

