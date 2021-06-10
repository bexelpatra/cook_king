package com.example.demo.service;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.UsersEntity;

import java.util.Optional;

public interface UserService {

    Optional<UsersEntity> findUsersEntityByToken(String token);
    Optional<UsersEntity> logInByEmailAndPassword(String email,String pw);
    Optional<UsersEntity> findUsersEntityByEmail(String email);
    Optional<UsersEntity> signUp(UsersDto usersDto);

}
