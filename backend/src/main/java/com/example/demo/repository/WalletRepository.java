package com.example.demo.repository;

import com.example.demo.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
    Optional<WalletEntity> findByUsersEntityId(int usersId);
    Optional<WalletEntity> findTopByPublicKey(String publicKey);
}
