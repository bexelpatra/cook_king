package com.example.demo.repository;

import com.example.demo.entity.KeyEntity;
import jdk.nashorn.internal.runtime.regexp.joni.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<KeyEntity,Long> {
    Optional<KeyEntity> findByUsersEntityId(int usersId);
    Optional<KeyEntity> findTopByPublicKey(String publicKey);
}
