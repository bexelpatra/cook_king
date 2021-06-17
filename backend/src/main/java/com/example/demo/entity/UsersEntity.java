package com.example.demo.entity;

import com.example.demo.enums.PinKind;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "mydb")
@NoArgsConstructor
public class UsersEntity {
    private int id;
    private String token;
    private String password;
    private Date regDate;
    private String email;
    private String pin;
    private PinKind pinKind;
    private String nickname;
    private boolean autoLogIn;

    @JsonIgnore
    private List<RecipesEntity> recipesEntities;
    @JsonIgnore
    private List<RecipesEntity> usersFavoriteRecipes;

    @Builder
    public UsersEntity(int id, String token, String password, Date regDate, String email, String pin, PinKind pinKind, String nickname, boolean autoLogIn) {
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
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "reg_date")
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id &&
                autoLogIn == that.autoLogIn &&
                Objects.equals(token, that.token) &&
                Objects.equals(password, that.password) &&
                Objects.equals(regDate, that.regDate) &&
                Objects.equals(email, that.email) &&
                Objects.equals(pin, that.pin) &&
                pinKind == that.pinKind &&
                Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, password, regDate, email, pin, pinKind, nickname, autoLogIn);
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "pin")
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Basic
    @Column(name = "pin_kind")
    public PinKind getPinKind() {
        return pinKind;
    }

    public void setPinKind(PinKind pinKind) {
        this.pinKind = pinKind;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) { this.nickname = nickname; }

    @Basic
    @Column(name = "auto_log_in")
    public boolean isAutoLogIn() { return autoLogIn; }

    public void setAutoLogIn(boolean autoLogIn) { this.autoLogIn = autoLogIn; }

    @OneToMany(mappedBy = "usersEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<RecipesEntity> getRecipesEntities() { return recipesEntities; }
    public void setRecipesEntities(List<RecipesEntity> recipesEntities) { this.recipesEntities = recipesEntities; }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "users_favorite_recipes" ,
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipes_id")}
    )
    public List<RecipesEntity> getUsersFavoriteRecipes() { return usersFavoriteRecipes; }
    public void setUsersFavoriteRecipes(List<RecipesEntity> usersFavoriteRecipes) { this.usersFavoriteRecipes = usersFavoriteRecipes; }

}
