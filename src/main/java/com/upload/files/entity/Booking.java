package com.upload.files.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upload.files.repository.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private int orderPrice;
    private int orderQty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOut;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /*@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Booking> bookingList = new ArrayList<Booking>();*/


}
