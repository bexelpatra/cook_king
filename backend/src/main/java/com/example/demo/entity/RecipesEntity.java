package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recipes", schema = "mydb")
public class RecipesEntity {
    private int id;
    private String title;
    private String stuffs;

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
}
