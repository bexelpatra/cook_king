package com.example.demo.serviceImpl;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Optional;

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
        if(!Utils.isValidEmail(email)) return Optional.empty();
        if(!emailService.check(email)) return Optional.empty();
        if(usersDto.getPassword().length()<8) return Optional.empty();
        if(usersRepository.findUsersEntityByEmail(email).isPresent()) return Optional.empty();
        String password = "";

        try {
            password = new AES().aesDecode(usersDto.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(usersRepository.save(UsersEntity.builder()
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

}
