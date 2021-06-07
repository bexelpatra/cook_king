package com.example.demo.serviceImpl;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.LogIn;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LogIns implements LogIn {
    private final UserService userService;
    @Override
    public Optional<UsersDto> logIn(int type, String token, String mail, String pw) {
        Optional<UsersDto> usersDto = Optional.empty();
        Optional<UsersEntity> optionalUsersEntity = Optional.empty();

        if(type == -1) return usersDto;
        if(token.equals("")&&mail.equals("")) return usersDto;

        switch (type){
            // token 로그인
            case 0:
                optionalUsersEntity = logInByToken(token);
                break;
            // id, pw 로그인
            case 1:
                optionalUsersEntity = logInByPassword(mail,pw);
                break;
        }

        if(optionalUsersEntity.isPresent()){
            UsersDto.fix(Utils.to(UsersDto.class,optionalUsersEntity));
        }

        return usersDto;
    }


    private Optional<UsersEntity> logInByToken(String token){
        return userService.findUsersEntityByToken(token);
    }
    private Optional<UsersEntity> logInByPassword(String email, String password){
        String encoded = "0";
        try {
            encoded = new AES().aesEncode(password);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        String finalEncoded = encoded;
        return userService.findUsersEntityByEmail(email).filter(usersEntity -> usersEntity.getPassword().equals(finalEncoded));
    }
}
