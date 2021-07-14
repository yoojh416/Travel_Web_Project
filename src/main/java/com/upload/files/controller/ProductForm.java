package com.upload.files.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm { //컨테이너(상품 관련 컨트롤러 돌리기 위한 용도)

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

    private int fno;
    private Long reNo;

    public ProductForm() {}

    public ProductForm(Long proNo, String proTitle, String proWriter, int price
            , String proContent, String region, String season, String theme, String N
            , String E, int fno, Long reNo) {
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
        this.fno = fno;
        this.reNo = reNo;
    }
}
