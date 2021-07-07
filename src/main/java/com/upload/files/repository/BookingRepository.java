package com.upload.files.repository;

import com.upload.files.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //order CRUD 용
}
