package com.example.demo.dto;

import com.example.demo.entity.CommentEntity;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    public final Class transfer = CommentEntity.class;

    private int id;
    private String content;
    private Date regDate;
}
