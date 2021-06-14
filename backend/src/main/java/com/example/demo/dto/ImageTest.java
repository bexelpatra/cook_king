package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageTest {

    private int index;
    private MultipartFile file;
    private String text;
}
