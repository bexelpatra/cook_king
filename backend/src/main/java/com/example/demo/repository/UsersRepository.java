package com.example.demo.repository;

import com.example.demo.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    UsersEntity findUsersEntityById(long id);
    Optional<UsersEntity> findUsersEntityByToken(String token);
    List<UsersEntity> findUsersEntitiesByIdGreaterThanEqual(long id);
    Optional<UsersEntity> findUsersEntityByEmail(String email);
}
