package com.example.demo.serviceImpl;

import com.example.demo.dto.RecipesDto;
import com.example.demo.dto.UsersDto;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    @Override
    public Optional<UsersEntity> findUsersEntityByToken(String token) {
        return usersRepository.findUsersEntityByToken(token);
    }

    @Override
    public Optional<UsersEntity> logInByEmailAndPassword(String email, String pw) {
        return Optional.empty();
    }

    @Override
    public Optional<UsersEntity> findUsersEntityByEmail(String email) {
        return usersRepository.findUsersEntityByEmail(email);
    }

    @Override
    public Optional<UsersEntity> signUp(UsersDto usersDto) {
        String email = usersDto.getEmail();
        /*
            입력값 검증
            1. 이메일 유효성 검사
            2. 인증 완료한 이메일인지 검사
            3. 비밀번호가 8자리 이상인지 검사
            4. 기존 회원인지 검사
         */
        if(!Utils.isValidEmail(email)) {
            return Optional.empty();
        }
        boolean flag = emailService.check(email);
        if(!flag) return Optional.empty();
        if(usersDto.getPassword().length()<8) return Optional.empty();
        if(usersRepository.findUsersEntityByEmail(email).isPresent()) return Optional.empty();
        String password = "";
        try {
//            password = new AES().aesEncode(usersDto.getPassword());
            password = BCrypt.hashpw(usersDto.getPassword(),BCrypt.gensalt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = Base64.getEncoder().encodeToString(usersDto.getEmail().getBytes());
        return Optional.ofNullable(usersRepository.save(UsersEntity.builder()
                .token(token)
                .email(usersDto.getEmail())
                .autoLogIn(true)
                .password(password)
                .regDate(new Date())
                .build()));
    }

    @Override
    public Integer addFavoriteRecipe(int usersId, int recipeId) {
        Integer result = 0;
        String m = "";
        try{
            result = usersRepository.addFavoriteRecipe(usersId,recipeId);
        }catch (Exception e){
            m = e.getMessage().split(";")[2];
            if(m.contains("null")){
                result = 3;
            }else if(m.toLowerCase().contains("primary")){
                result = usersRepository.deleteFavoriteRecipe(usersId,recipeId);
                if(result == 1) result = 2;
            }
        }
        return result;
    }

    @Override
    public UsersEntity addFavoriteRecipe(UsersEntity user, RecipesEntity recipesEntity) {
        List<Integer> recipeIds = user.getUsersFavoriteRecipes().stream().map(RecipesEntity::getId).collect(Collectors.toList());
        List<RecipesEntity> recipesEntities = user.getUsersFavoriteRecipes();
        if(recipeIds.contains(recipesEntity.getId())){
            recipesEntities.removeIf(recipes ->{
                return recipes.getId() == recipesEntity.getId();
            });
            user.setRecipesEntities(recipesEntities);
        }else {
            user.getUsersFavoriteRecipes().add(recipesEntity);
        }
        return usersRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public List<Integer> getFavoriteInteger(UsersEntity usersEntity) {
        List<RecipesEntity> recipesEntities = usersEntity.getRecipesEntities();
        List<Integer> integers = recipesEntities.stream().map(RecipesEntity::getId).collect(Collectors.toList());
        return integers;
    }

    private List<RecipesDto> recipeConverter(List<RecipesEntity> recipesEntities){
        return recipesEntities.stream().map(RecipesEntity::toWithContents).collect(Collectors.toList());
    }
    @Override
    public UsersDto getUserInfo(UsersEntity usersEntity) {
        if(usersEntity == null) return null;
        UsersDto usersDto = Utils.to(UsersDto.class,usersEntity);
        UsersDto.fix(usersDto);
        usersDto.setFavorite(getFavoriteInteger(usersEntity));
        usersDto.setMyRecipe(recipeConverter(usersEntity.getRecipesEntities()));
        usersDto.setMyFavoriteRecipe(recipeConverter(usersEntity.getUsersFavoriteRecipes()));
        return usersDto;
    }


}
