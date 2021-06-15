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

    private ContentKind contentKind;

    @Builder
    public ContentDto(int id, String path, String url, String title, int order, String description, ContentKind contentKind) {
        this.id = id;
        this.path = path;
        this.url = url;
        this.title = title;
        this.order = order;
        this.description = description;
        this.contentKind = contentKind;
    }

}
