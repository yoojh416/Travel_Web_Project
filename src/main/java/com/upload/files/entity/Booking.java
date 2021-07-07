package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "booking")
@Getter @Setter
public class Booking {

    @Id
    @GeneratedValue
    private Long OrderId;
    private int OrderPrice;
    private int OrderQty;
    private LocalDate OrderDate;
    private LocalDate dateIn;
    private LocalDate dateOut;

}
