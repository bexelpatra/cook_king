package com.example.demo.dto;

import com.example.demo.entity.BoardEntity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BoardDto {
    private int id;
    private String title;
    private String content;
    private Date regDate;

    @Builder
    public BoardDto(int id, String title, String content, Date regDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
    }

    public static  BoardEntity toEntity(BoardDto boardDto,ObjectMapper objectMapper){
        if(boardDto == null) return null;
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        return objectMapper.convertValue(boardDto,BoardEntity.class);
    }

    public static BoardDto toDto(BoardEntity boardEntity,ObjectMapper objectMapper){
        if(boardEntity == null) return null;
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        return objectMapper.convertValue(boardEntity,BoardDto.class);
    }

}
