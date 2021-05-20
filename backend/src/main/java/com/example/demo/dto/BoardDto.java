package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private Date regDate;


}
