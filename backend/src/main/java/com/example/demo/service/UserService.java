package com.example.demo.service;

import com.example.demo.entity.UsersEntity;

import java.util.Optional;

public interface UserService {

    Optional<UsersEntity> findUsersEntityByToken(String token);
}
