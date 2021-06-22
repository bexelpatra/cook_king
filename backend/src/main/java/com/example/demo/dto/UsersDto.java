package com.example.demo.dto;

import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.PinKind;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data // getter, setter
@NoArgsConstructor
public class UsersDto {
    private int id =0;
    private String token = null;
    private String password = null;
    private Date regDate = null;
    private String email = null;
    private String pin = null;
    private PinKind pinKind =null;
    private String nickname = null;
    private boolean autoLogIn = true;

    private List<Integer> favorite = new ArrayList<>();
    private List<RecipesDto> myRecipe = new ArrayList<>();
    private List<RecipesDto> myFavoriteRecipe = new ArrayList<>();

//    Entity에서는 @Builder 사용시 반드시 @NoargsConstructor를 사용해야 한다.
    // 아래 생성자는 @NoargsConstructor와 동일하다.
//    public UsersDto() {
//
//    }

    @Builder
    public UsersDto(int id, String token, String password, Date regDate, String email, String pin, PinKind pinKind, String nickname, boolean autoLogIn) {
        this.id = id;
        this.token = token;
        this.password = password;
        this.regDate = regDate;
        this.email = email;
        this.pin = pin;
        this.pinKind = pinKind;
        this.nickname = nickname;
        this.autoLogIn = autoLogIn;
    }


    // ---------------------------------------------------------------------------
    // public 접근제어자, void, int ... 반환값의 타입, 메소드 이름, (parameter) 값
//    public void rVoid(){
//
//    }
//    public int publicInt(){
//
//        return 1;
//    }
//    private int privateInt(){
//        return 2;
//    }
    // ---------------------------------------------------------------------------

    public static UsersDto toDto(UsersEntity usersEntity, ObjectMapper objectMapper){
        if(usersEntity==null) return null;
        if(objectMapper==null) return null;

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        return objectMapper.convertValue(usersEntity,UsersDto.class);
    }

    public static List<UsersDto> toDto(List<UsersEntity> usersEntityList, ObjectMapper objectMapper) {

        // 반환할 값
        List<UsersDto> usersDtos = new ArrayList<>();

        // 값 확인
        if (usersEntityList == null) return null;
        if (usersEntityList.size() == 0) return usersDtos;
        if (objectMapper == null) return null;

        // 향상된 for 문
//        for (UsersEntity usersEntity in usersEntityList) {

//        for (int i=0;i < usersEntityList.size();i++) {
//        UsersEntity usersEntity = usersEntityList.get(i);
//        userDtos.add(usersEntity);
//          }

        for (UsersEntity usersEntity : usersEntityList) {
            usersDtos.add(toDto(usersEntity,objectMapper));
        }

        return usersDtos;
    }

    public static UsersEntity toEntity(UsersDto usersDto,ObjectMapper objectMapper){
        if(usersDto==null) return null;
        if(objectMapper==null) return null;

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        return objectMapper.convertValue(usersDto,UsersEntity.class);
    }

    public static UsersDto fix(UsersDto usersDto){
        if(usersDto == null) return null;
        usersDto.setPassword(null);
        return usersDto;
    }

}
