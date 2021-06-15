package com.example.demo.entity;

import com.example.demo.enums.ContentKind;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "content", schema = "mydb")
@NoArgsConstructor
public class ContentEntity {
    private int id;
    private String path;
    private String name;
    private String url;
    private String title;
    private int order;
    private String description;

    private ContentKind contentKind;

    private RecipesEntity recipesEntity;

    @Builder
    public ContentEntity(int id, String path, String url, String title, int order, String description, ContentKind contentKind, RecipesEntity recipesEntity,String name) {
        this.id = id;
        this.path = path;
        this.url = url;
        this.title = title;
        this.order = order;
        this.description = description;
        this.contentKind = contentKind;
        this.recipesEntity = recipesEntity;
        this.name= name;
    }

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
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Basic
    @Column(name = "urls")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Column(name = "orders")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "kind")
    public ContentKind getContentKind() { return contentKind; }
    public void setContentKind(ContentKind contentKind) { this.contentKind = contentKind; }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipes_id")
    public RecipesEntity getRecipesEntity() { return recipesEntity; }

    public void setRecipesEntity(RecipesEntity recipesEntity) { this.recipesEntity = recipesEntity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentEntity that = (ContentEntity) o;
        return id == that.id &&
                Objects.equals(path, that.path) &&
                Objects.equals(url, that.url) &&
                Objects.equals(title, that.title) &&
                Objects.equals(order, that.order) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, url, title, order, description);
    }
}
