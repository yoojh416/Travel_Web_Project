package com.upload.files.service;

import com.upload.files.entity.Booking;
import com.upload.files.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    /*private final MemberRepository memberRepository;*/

    @Transactional
    public void save(Booking order) {
        bookingRepository.save(order);
    }

    public List<Booking> order() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById (Long orderId) {
        return bookingRepository.findById(orderId);
    }

}
