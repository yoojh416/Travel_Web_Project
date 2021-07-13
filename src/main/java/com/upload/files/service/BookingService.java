package com.upload.files.service;

import com.upload.files.entity.Booking;
import com.upload.files.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    /*private final MemberRepository memberRepository;*/

    public void save(Booking order) {
        bookingRepository.save(order);
    }

/*    public List<Booking> findAllById(Long orderId) {
        return bookingRepository.findAllById(orderId);
    }*/

    public Optional<Booking> findById(Long orderId) {
        return bookingRepository.findById(orderId);
    }

    public void deleteById(Long orderId) {
        bookingRepository.deleteById(orderId);
    }

}
