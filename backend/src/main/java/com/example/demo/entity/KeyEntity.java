package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "key", schema = "mydb")
public class KeyEntity {
    private int id;
    private String publicKey;
    private String privateKey;
    private UsersEntity usersEntity;

    public KeyEntity(String publicKey, String privateKey, UsersEntity usersEntity) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.usersEntity = usersEntity;
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
    @Column(name = "public_key")
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Basic
    @Column(name = "private_key")
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    public UsersEntity getUsersEntity() { return usersEntity; }
    public void setUsersEntity(UsersEntity usersEntity) { this.usersEntity = usersEntity; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyEntity keyEntity = (KeyEntity) o;
        return id == keyEntity.id &&
                Objects.equals(publicKey, keyEntity.publicKey) &&
                Objects.equals(privateKey, keyEntity.privateKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicKey, privateKey);
    }
}
