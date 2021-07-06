package com.upload.files.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm {

    private Long proNo;
    private String proTitle;
    private String proWriter;
    private int price;
    private String proContent;
    private String region;
    private String season;
    private String theme;

    public ProductForm(Long proNo, String proTitle, String proWriter, int price, String proContent, String region, String season, String theme) {
        this.proNo = proNo;
        this.proTitle = proTitle;
        this.proWriter = proWriter;
        this.price = price;
        this.proContent = proContent;
        this.region = region;
        this.season = season;
        this.theme = theme;
    }

    public ProductForm() {}

}
