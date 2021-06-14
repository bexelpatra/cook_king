package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class TestDto2 {

    @JsonAlias({"imgs","files"})
    private MultipartFile[] file;

    private String[] name;
    private Integer[] index;
    private String[] text;


}
