package com.example.demo.dto;

import com.example.demo.enums.ContentKind;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentDto {
    private int id;
    private String path;
    private String name;
    private String url;
    private String title;
    private int order;
    private String description;
    private String absolutePath;

    private ContentKind contentKind;

    private byte[] bytes;

    @Builder
    public ContentDto(int id, String path, String url, String title, int order, String description, ContentKind contentKind,String absolutePath) {
        this.id = id;
        this.path = path;
        this.url = url;
        this.title = title;
        this.order = order;
        this.description = description;
        this.contentKind = contentKind;
        this.absolutePath = absolutePath;
    }


    public ContentDto setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
}
