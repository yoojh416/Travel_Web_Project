package com.upload.files.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "booking")
@Getter @Setter
public class Booking {

    @Id
    @GeneratedValue
    private Long orderId;
    private int orderPrice;
    private int orderQty;

    @Column(name = "order_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date orderDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date dateIn;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date dateOut;

}
