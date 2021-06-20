package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TestDto {
    private int i1=0;
    private int i2=0;
    private int i3=0;
    private int i4=0;

    private String s1="";
    private String s2="";
    private String s3="";
    private String s4="";

    private float f1 = 0;
    private float f2 = 0;
    private float f3 = 0;
    private float f4 = 0;
    @JsonAlias(value = {"img","files"})
    private MultipartFile multipartFile;
    @Builder
    public TestDto(int i1, int i2, int i3, int i4, String s1, String s2, String s3, String s4, float f1, float f2, float f3, float f4) {
        this.i1 = i1;
        this.i2 = i2;
        this.i3 = i3;
        this.i4 = i4;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
    }
}
