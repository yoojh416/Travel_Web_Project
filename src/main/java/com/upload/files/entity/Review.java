package com.upload.files.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Review {

    @Id @GeneratedValue
    private Long reNo;

    @Column
    private String reTitle;

    @Column
    private String reWriter;

    @Column
    private String reContent;

}


