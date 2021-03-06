package com.example.demo.entity;

import com.example.demo.dto.*;
import com.example.demo.enums.ContentKind;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "recipes", schema = "mydb")
@JsonAutoDetect
public class RecipesEntity {

    private int id;
    private String title;
    private String stuffs;
    private String description="";
    private FirstCategoryEntity firstCategoryEntity;
    private SecondCategoryEntity secondCategoryEntity;

    private FirstCategoryKind firstCategoryKind;
    private SecondCategoryKind secondCategoryKind;

    private CuisineEntity cuisineEntity;
    @JsonIgnore
    private List<ContentEntity> contentEntities;
    @JsonIgnore
    private UsersEntity usersEntity;
    @JsonIgnore
    private List<UsersEntity> likeUsersEntities;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "stuffs")
    public String getStuffs() {
        return stuffs;
    }

    public void setStuffs(String stuffs) {
        this.stuffs = stuffs;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Basic
//    @Enumerated(EnumType.ORDINAL)
    @Column(name = "first_category")
    public FirstCategoryKind getFirstCategoryKind() { return firstCategoryKind; }
    public void setFirstCategoryKind(FirstCategoryKind firstCategoryKind) { this.firstCategoryKind = firstCategoryKind; }

    @Basic
//    @Enumerated(EnumType.ORDINAL)
    @Column(name = "second_category")
    public SecondCategoryKind getSecondCategoryKind() { return secondCategoryKind; }
    public void setSecondCategoryKind(SecondCategoryKind secondCategoryKind) { this.secondCategoryKind = secondCategoryKind; }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_category_id")
    public FirstCategoryEntity getFirstCategoryEntity() { return firstCategoryEntity; }
    public void setFirstCategoryEntity(FirstCategoryEntity firstCategoryEntity) { this.firstCategoryEntity = firstCategoryEntity; }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_category_id")
    public SecondCategoryEntity getSecondCategoryEntity() { return secondCategoryEntity; }
    public void setSecondCategoryEntity(SecondCategoryEntity secondCategoryEntity) { this.secondCategoryEntity = secondCategoryEntity; }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cuisine_id")
    public CuisineEntity getCuisineEntity() { return cuisineEntity; }
    public void setCuisineEntity(CuisineEntity cuisineEntity) { this.cuisineEntity = cuisineEntity; }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "recipesEntity")
    public List<ContentEntity> getContentEntities() { return contentEntities; }
    public void setContentEntities(List<ContentEntity> contentEntities) { this.contentEntities = contentEntities; }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    public UsersEntity getUsersEntity() { return usersEntity; }
    public void setUsersEntity(UsersEntity usersEntity) { this.usersEntity = usersEntity; }

    @ManyToMany(mappedBy = "usersFavoriteRecipes" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<UsersEntity> getLikeUsersEntities() { return likeUsersEntities; }
    public void setLikeUsersEntities(List<UsersEntity> likeUsersEntities) { this.likeUsersEntities = likeUsersEntities; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipesEntity that = (RecipesEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(stuffs, that.stuffs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, stuffs);
    }

    public RecipesDto to(){
        String stuff = this.getStuffs() == null ? this.stuffs ="": this.getStuffs();
        ContentEntity content = this.getContentEntities().stream().filter(contentEntity -> contentEntity.getContentKind().getValue() == ContentKind.TITLE.getValue()).findFirst().orElse(null);
        byte[] bytes ={};
        try {
            bytes = Files.readAllBytes(Paths.get(content.getAbsolutePath()+content.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Utils.to(RecipesDto.class,this)
                .setStuffList(Arrays.asList(stuff.split("#")))
                .setUsersDto(UsersDto.fix(Utils.to(UsersDto.class,this.getUsersEntity())))
                .setBytes(bytes);
    }
    public RecipesDto toWithContents(){
        UsersDto usersDto = Utils.to(UsersDto.class,this.getUsersEntity());
        UsersDto.fix(usersDto);
        String stuff = this.getStuffs() == null ? this.stuffs ="": this.getStuffs();
        String url = "imgs/default.png";
        ContentEntity content = this.getContentEntities().stream().filter(contentEntity -> contentEntity.getContentKind().getValue() == ContentKind.TITLE.getValue()).findFirst().orElse(null);
        if(content!=null) url = content.getUrl();
        byte[] bytes ={};
        try {
            bytes = Files.readAllBytes(Paths.get(content.getAbsolutePath()+content.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        List<ContentDto> contentDtos = Utils.to(ContentDto.class,this.getContentEntities());
        List<ContentDto> contentDtos = this.getContentEntities().stream().map(contentEntity -> {
            try {
                return Utils.to(ContentDto.class,contentEntity).setBytes(Files.readAllBytes(Paths.get(contentEntity.getAbsolutePath()+contentEntity.getName())));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        return Utils.to(RecipesDto.class,this)
                .setStuffList(Arrays.asList(stuff.split("#")))
//                .setUsersDto(UsersDto.fix(Utils.to(UsersDto.class,this.getUsersEntity())))
                .setUsersDto(usersDto)
                .setContentList(contentDtos)
                .setUrl(url)
                .setBytes(bytes);


    }
}
