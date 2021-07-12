package com.upload.files.controller;

import com.querydsl.core.types.Order;
import com.upload.files.entity.Booking;
import com.upload.files.repository.BookingRepository;
import com.upload.files.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@Transactional(readOnly = true)
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private EntityManager em;

    @GetMapping("/book") //상품 예약
    public String main(Model model) {
        model.addAttribute("order", new Booking());
        return "order/Booking";
    }

    @PostMapping("/confirm") //예약 내용 확인
    public String save(Booking booking, Model model) {
        bookingService.save(booking);
        model.addAttribute("order", booking);
        return "order/Confirmation";
    }

    @GetMapping("/modify/{orderId}")
    public String getOrderUpdate(Model model, @PathVariable Long orderId) {
        Optional<Booking> booking = bookingService.findById(orderId);
        model.addAttribute("bookingInfo", booking);

        return "order/ModifyOrder";
    }

    @PostMapping(value = "/modify/{orderId}")
    @Transactional
    public String setOrderUpdate(Model model, @PathVariable Long orderId, Booking updateOrder) {
        Booking booking = bookingService.findById(orderId).get();
        booking.setOrderId(updateOrder.getOrderId());
        booking.setDateIn(updateOrder.getDateIn());
        booking.setDateOut(updateOrder.getDateOut());
        booking.setOrderDate(updateOrder.getOrderDate());
        booking.setOrderPrice(updateOrder.getOrderPrice());
        booking.setOrderQty(updateOrder.getOrderQty());
        bookingService.save(booking);
        model.addAttribute("order", booking);

        return "order/Confirmation";
    }

    @GetMapping("/cancel") //예약 취소
    @Transactional
    public String deleteOrder(Long orderId) {
        int isSuccessful = em.createQuery("delete from booking b where b.order_id=:order_id")
                .setParameter("order_id", orderId)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("이미 삭제 된 주문건 입니다.");
        }
        return "/";
    }

}

