package com.upload.files.controller;

import com.upload.files.entity.Booking;
import com.upload.files.entity.Product;
import com.upload.files.repository.BookingRepository;
import com.upload.files.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/order")
@Transactional
public class BookingController {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private ProductService productService;

    /** 예약페이지로 이동 */
    @GetMapping("/book/{proNo}")
    public String main(Model model, @PathVariable Long proNo) {
        model.addAttribute("order", new Booking());

        Product product = productService.findOne(proNo);
        model.addAttribute("product", product);

        return "order/booking";
    }

    /** 예약된 내용 저장해서 확인 페이지로 보냄 */
    @PostMapping("/confirm")
    public String save(Booking booking, HttpServletRequest request, Model model) {
        booking.setOrderPrice(Integer.parseInt(request.getParameter("OrderPrice")));
        bookingRepository.save(booking);
        model.addAttribute("order", booking);

        return "order/Confirmation";
    }

    /** 변경 페이지 확인 */
    @GetMapping("/modify/{orderId}")
    public String getOrderUpdate(@PathVariable("orderId") Long orderId, Model model) {
        Booking booking = bookingRepository.findById(orderId).get();
        model.addAttribute("order", booking);

        return "order/modify";
    }

    /** 변경 진행 후 확인 */
    @PostMapping("/modify/{orderId}")
    public String setOrderUpdate(Model model, @PathVariable Long orderId, Booking updateOrder) {
        Booking booking = bookingRepository.findById(orderId).get();

        booking.setDateIn(updateOrder.getDateIn());
        booking.setDateOut(updateOrder.getDateOut());
        booking.setOrderQty(updateOrder.getOrderQty());
        bookingRepository.save(booking);

        model.addAttribute("order", booking);

        /*List<Booking> bookingList = bookingRepository.findAll();
        model.addAttribute("order", bookingList);*/

        return "order/Confirmation";
    }

    /** 취소 */
    @GetMapping("/cancel/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        bookingRepository.deleteById(orderId);

        return "redirect:/";
    }

    /** booking list로 가기 */
    @GetMapping("/list")
    public String goBookingList(Booking Booking, Model model) {
        List<Booking> bookingList = bookingRepository.findAll();

        model.addAttribute("orders", bookingList);

        return "order/bookingList";
    }

}