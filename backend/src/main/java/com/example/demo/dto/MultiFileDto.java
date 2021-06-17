package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MultiFileDto {
    private Integer[] order;
    @JsonAlias(value = {"files","imgs"})
    private MultipartFile[] file;
    private String[] text;
    private Integer[] kind;

}