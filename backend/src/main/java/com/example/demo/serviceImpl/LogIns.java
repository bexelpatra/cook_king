package com.example.demo.serviceImpl;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.LogIn;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sun.awt.image.IntegerInterleavedRaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LogIns implements LogIn {
    private final UserService userService;
    private final List<LogIn> logIns;

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
            UsersEntity usersEntity = optionalUsersEntity.get();
            UsersDto user = UsersDto.fix(Utils.to(UsersDto.class,usersEntity));

            List<Integer> favorite = new ArrayList<>();
            favorite = usersEntity.getUsersFavoriteRecipes()
                    .stream()
                    .map(RecipesEntity::getId)
                    .collect(Collectors.toList());

            user.setFavorite(favorite);

            usersDto = Optional.of(user);
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
        return userService.findUsersEntityByEmail(email).filter(usersEntity1 -> usersEntity1.getPassword().equals(finalEncoded));
    }
}
