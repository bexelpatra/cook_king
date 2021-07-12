package com.example.demo.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.PublicKey;
import java.util.Objects;

@Entity
@Table(name = "wallet", schema = "mydb")
@NoArgsConstructor
public class WalletEntity {
    private int id;
    private String publicKey;
    private String privateKey;
    private UsersEntity usersEntity;

    public WalletEntity(String publicKey, String privateKey, UsersEntity usersEntity) {
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
        WalletEntity walletEntity = (WalletEntity) o;
        return id == walletEntity.id &&
                Objects.equals(publicKey, walletEntity.publicKey) &&
                Objects.equals(privateKey, walletEntity.privateKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicKey, privateKey);
    }

}
