package com.example.demo.controller;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    @GetMapping(value = {"/user"})
    public ResponseEntity signIn(@RequestParam(value = "token") Optional<String> token){
        Map<String, Object> result = new HashMap<>();
        HttpStatus status = null;

        Optional<UsersEntity> optionalUsersEntity = userService.findUsersEntityByToken(token.orElse(""));
        UsersEntity usersEntity = optionalUsersEntity.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        UsersDto usersDto = Utils.to(UsersDto.class,usersEntity);
        UsersDto.fix(usersDto);

        // 결과 출력
        result.put("user",usersDto);
        result.put("desc","성공적으로 사용자 정보를 조회했습니다.");
        status = HttpStatus.OK;
        return new ResponseEntity(result,status);
    }

    // 메일 인증번호 발송하기
    @PostMapping(value = {"/mail-cert-number"})
    public ResponseEntity mailCertification(@RequestParam(value = "email")String receiver){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        // 입력 데이터 검증
        if(!Utils.isValidEmail(receiver)) throw new RuntimeException("이메일 주소가 잘못되었습니다.");
        
        boolean flag = emailService.sendCertMail(receiver);
        
        // 결과 출력
        StringBuilder desc = new StringBuilder();
        if(flag) {
            desc.append(String.format("[%s]로 인증번호를 발송했습니다.",receiver));
            httpStatus = HttpStatus.OK;
        }else {
            desc.append("이메일전송에 실패했습니다.");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }
        result.put("desc",desc.toString());
        return new ResponseEntity(result,httpStatus);
    }

    // 메일 인증하기
    @GetMapping(value = {"/mail-cert-number"})
    public ResponseEntity checkCertification(@RequestParam(value = "email")String receiver,
                                             @RequestParam(value = "number")String number){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        boolean b =  emailService.check(receiver);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
