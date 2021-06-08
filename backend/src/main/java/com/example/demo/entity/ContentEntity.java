package com.example.demo.entity;

import com.example.demo.enums.ContentKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "content", schema = "mydb")
public class ContentEntity {
    private int id;
    private String path;
    private String url;
    private String title;
    private int order;
    private String description;

    private ContentKind contentKind;

    private RecipesEntity recipesEntity;

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
    @Column(name = "url")
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
    @Column(name = "order")
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
