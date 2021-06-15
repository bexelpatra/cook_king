package com.example.demo.entity;

import com.example.demo.enums.SecondCategoryKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "second_category", schema = "mydb")
public class SecondCategoryEntity {
    private int id;
    private String name;
    private SecondCategoryKind kind;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "kind")
    public SecondCategoryKind getKind() { return kind; }
    public void setKind(SecondCategoryKind kind) { this.kind = kind; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondCategoryEntity that = (SecondCategoryEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
