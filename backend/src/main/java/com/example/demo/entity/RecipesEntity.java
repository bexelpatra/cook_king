package com.example.demo.entity;

import com.example.demo.dto.*;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipes", schema = "mydb")
public class RecipesEntity {
//    public final Class convert = RecipesDto.class;

    private int id;
    private String title;
    private String stuffs;

    private FirstCategoryEntity firstCategoryEntity;
    private SecondCategoryEntity secondCategoryEntity;
    private CuisineEntity cuisineEntity;
    @JsonIgnore
    private List<ContentEntity> contentEntities;

    @Id
    @Column(name = "id")
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

    @OneToOne
    public FirstCategoryEntity getFirstCategoryEntity() { return firstCategoryEntity; }
    public void setFirstCategoryEntity(FirstCategoryEntity firstCategoryEntity) { this.firstCategoryEntity = firstCategoryEntity; }

    @OneToOne
    public SecondCategoryEntity getSecondCategoryEntity() { return secondCategoryEntity; }

    public void setSecondCategoryEntity(SecondCategoryEntity secondCategoryEntity) { this.secondCategoryEntity = secondCategoryEntity; }
    @OneToOne
    public CuisineEntity getCuisineEntity() { return cuisineEntity; }
    public void setCuisineEntity(CuisineEntity cuisineEntity) { this.cuisineEntity = cuisineEntity; }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "recipesEntity")
    public List<ContentEntity> getContentEntities() { return contentEntities; }
    public void setContentEntities(List<ContentEntity> contentEntities) { this.contentEntities = contentEntities; }

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
        return Utils.to(RecipesDto.class,this)
                .setContentDtos(Utils.to(ContentDto.class,this.contentEntities))
                .setFirstCategoryDto(Utils.to(FirstCategoryDto.class,this.firstCategoryEntity))
                .setSecondCategoryDto(Utils.to(SecondCategoryDto.class,this.secondCategoryEntity))
                .setCuisineDto(Utils.to(CuisineDto.class,this.cuisineEntity))
                ;
    }
}
