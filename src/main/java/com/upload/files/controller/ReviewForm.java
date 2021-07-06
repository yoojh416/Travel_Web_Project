package com.upload.files.controller;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@ToString
public class ReviewForm {

    @Id @GeneratedValue
    private Long reNo;

    private String reTitle;

    @Column(length = 5000)
    private String reContent;

}
