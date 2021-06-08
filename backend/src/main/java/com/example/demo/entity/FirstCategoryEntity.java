package com.example.demo.entity;

import com.example.demo.enums.FirstCategoryKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "first_category", schema = "mydb")
public class FirstCategoryEntity {
    private int id;
    private String name;
    private FirstCategoryKind kind;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "kind")
    public FirstCategoryKind getKind() { return kind; }
    public void setKind(FirstCategoryKind kind) { this.kind = kind; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstCategoryEntity that = (FirstCategoryEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
