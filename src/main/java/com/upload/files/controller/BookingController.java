package com.upload.files.controller;

import com.upload.files.entity.Booking;
import com.upload.files.entity.Member;
import com.upload.files.entity.Product;
import com.upload.files.repository.BookingRepository;
import com.upload.files.repository.MemberRepository;
import com.upload.files.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/order")
@Transactional
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 예약페이지로 이동
     */
    @GetMapping("/book/{proNo}")
    public String main(Model model, @PathVariable Long proNo) {
        model.addAttribute("order", new Booking());

        Product product = productService.findOne(proNo);
        model.addAttribute("product", product);

        return "order/booking";
    }

    /**
     * 예약된 내용 저장해서 확인 페이지로 보냄
     */
    @PostMapping("/payment")
    public String save(Booking booking, HttpServletRequest request,
                       @AuthenticationPrincipal UserDetails userDetails, Model model) {

        Member member = memberRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("member", member);

        booking.setOrderPrice(Integer.parseInt(request.getParameter("OrderPrice")));
        booking.setOrderDate(LocalDate.now());
        booking.setMember(member);
        bookingRepository.save(booking);
        model.addAttribute("order", booking);

        int totalPrice = booking.getOrderPrice() * booking.getOrderQty();
        model.addAttribute("totalPrice", totalPrice);

        Product product = productService.findOne(Long.parseLong(request.getParameter("proNo")));
        model.addAttribute("product", product);

        return "order/payment";
    }

    @GetMapping("/confirmation/{orderId}")
    @Transactional
    public String checkingInfo(@PathVariable(name = "orderId") Long orderId, HttpServletRequest request,
                               @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Booking booking = bookingRepository.findByOrderId(orderId);
        model.addAttribute("order", booking);

        Member member = memberRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("member", member);

        return "order/confirmation";
    }

    /**
     * 취소
     */
    @GetMapping("/cancel/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        bookingRepository.deleteById(orderId);

        return "redirect:/";
    }

    /**
     * booking list로 가기
     */
    @GetMapping("/bookingInfo")
    public String goBookingList(Booking Booking, @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        Member member = memberRepository.findByUsername(userDetails.getUsername());
        Long id = member.getId();
        List<Booking> bookingList = bookingRepository.findByMemberId(id);

        model.addAttribute("orders", bookingList);

        return "order/bookingList";
    }

    //=============================================================================================//
    /**
     * 변경 페이지 확인
     */
    /*@GetMapping("/modify/{orderId}")
    public String getOrderUpdate(@PathVariable("orderId") Long orderId, Model model) {
        Booking booking = bookingRepository.findById(orderId).get();
        model.addAttribute("order", booking);

        return "order/modify";
    }*/

    /**
     * 변경 진행 후 확인
     */
    /*@PostMapping("/modify/{orderId}")
    public String setOrderUpdate(Model model, @PathVariable Long orderId, Booking updateOrder) {
        Booking booking = bookingRepository.findById(orderId).get();

        booking.setDateIn(updateOrder.getDateIn());
        booking.setDateOut(updateOrder.getDateOut());
        booking.setOrderQty(updateOrder.getOrderQty());
        bookingRepository.save(booking);

        model.addAttribute("order", booking);

        *//*List<Booking> bookingList = bookingRepository.findAll();
        model.addAttribute("order", bookingList);*//*

        return "order/confirmation";
    }*/

}