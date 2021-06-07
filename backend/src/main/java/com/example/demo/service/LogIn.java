package com.example.demo.service;

import com.example.demo.dto.UsersDto;

import java.util.Optional;

public interface LogIn {
    Optional<UsersDto> logIn(int type,String token, String mail, String pw);
}
