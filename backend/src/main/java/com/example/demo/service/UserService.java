package com.example.demo.service;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UsersEntity> findUsersEntityByToken(String token);

    Optional<UsersEntity> logInByEmailAndPassword(String email, String pw);

    Optional<UsersEntity> findUsersEntityByEmail(String email);

    Optional<UsersEntity> signUp(UsersDto usersDto);

    /**
     * @param usersId
     * @param recipeId
     * @return 0 : 실패, 1: 추가, 2: 삭제 , 3 : recipe를 찾을 수 없음
     */
    Integer addFavoriteRecipe(int usersId, int recipeId);
    UsersEntity addFavoriteRecipe(UsersEntity user, RecipesEntity recipesEntity);

    List<Integer> getFavoriteInteger(UsersEntity usersEntity);

    UsersDto getUserInfo(UsersEntity usersEntity);
}
