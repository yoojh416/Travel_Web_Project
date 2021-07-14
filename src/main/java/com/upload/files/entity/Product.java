package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "product")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue
    private Long proNo;
    private String proTitle;
    private String proWriter;
    private int price;
    private String proContent;
    private String region;
    private String season;
    private String theme;
    private String N; //위도
    private String E; //경도

    public Product(){}

    public Product(Long proNo, String proTitle, String proWriter, String proContent
            , String region, String season, String theme, int price, String N, String E) {
        this.proNo = proNo;
        this.proTitle = proTitle;
        this.proWriter = proWriter;
        this.price = price;
        this.proContent = proContent;
        this.region = region;
        this.season = season;
        this.theme = theme;
        this.N = N;
        this.E = E;
    }
}
