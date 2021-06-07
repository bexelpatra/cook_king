package com.example.demo.controller;

import com.example.demo.dto.UsersDto;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.EmailService;
import com.example.demo.service.LogIn;
import com.example.demo.service.UserService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    private final LogIn logIn;

    @GetMapping(value = "/mail-duplication")
    public ResponseEntity checkDuplicate(@RequestParam("email")String email){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        if(!Utils.isValidEmail(email)) {
            result.put("desc","메일 형식이 잘못되었습니다.");
            result.put("result",0);
            return new ResponseEntity(result,httpStatus);
        }

        if(userService.findUsersEntityByEmail(email).isPresent()){
            result.put("desc","이미 등록된 메일입니다.");
            result.put("result",0);
        }else {
            result.put("desc","사용가능한 이메일일비낟.");
            result.put("result",1);
        }
        return new ResponseEntity(result,httpStatus);
    }

    /**
     * 로그인 하기
     * @param token 사용자 토큰
     * @return 사용자 정보
     */
    @GetMapping(value = {"/user"})
    public ResponseEntity signIn(@RequestParam(value = "t") Optional<String> token,
                                 @RequestParam(value = "e") Optional<String> email,
                                 @RequestParam(value = "p") Optional<String> password,
                                 @RequestParam(value = "type", defaultValue = "-1") int type
                                 ){
        Map<String, Object> result = new HashMap<>();
        HttpStatus status = null;

        Optional<UsersDto> usersDto = logIn.logIn(type,token.orElse(""),email.orElse(""),password.orElse(""));
        if(usersDto.isPresent()){
            result.put("user",usersDto);
            result.put("desc","성공적으로 사용자 정보를 조회했습니다.");
            status = HttpStatus.OK;
        }else {
            result.put("desc","사용자 정보를 조회하지 못했습니다.");
            status = HttpStatus.NOT_IMPLEMENTED;
        }
        // 결과 출력
        return new ResponseEntity(result,status);
    }

    // 메일 인증번호 발송하기
    @PostMapping(value = {"/mail-certification"})
    public ResponseEntity mailCertification(@RequestParam(value = "email")String receiver){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        String response = emailService.sendCertMail(receiver);
        
        // 결과 출력
        StringBuilder desc = new StringBuilder();
        if(response == null) {
            desc.append(String.format("[%s]로 인증번호를 발송했습니다.",receiver));
            httpStatus = HttpStatus.OK;
        }else {
            desc.append("전송 샐패 ");
            desc.append("["+response+"]");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }
        result.put("desc",desc.toString());
        return new ResponseEntity(result,httpStatus);
    }

    // 메일 인증하기
    @GetMapping(value = {"/mail-certification"})
    public ResponseEntity checkCertification(@RequestParam(value = "email")String receiver,
                                             @RequestParam(value = "number")String number){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        boolean b =  emailService.certificate(receiver,number);

        if(b){
            httpStatus = HttpStatus.OK;
            result.put("desc","이메일 인증 성공");
        }else{
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            result.put("desc","이메일 인증 실패");
        }

        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/user")
    public ResponseEntity signUp(@RequestBody UsersDto usersDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        
        Optional<UsersEntity> usersEntity = null;
        try {
            usersEntity = userService.signUp(usersDto);
        }catch (Exception e){
            result.put("desc","회원 가입 실패 [이미 등록되었습니다]");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            return new ResponseEntity(result,httpStatus);
        }
        if(usersEntity.isPresent()){
            result.put("desc","회원가입되었습니다.");
            result.put("user", UsersDto.fix(Utils.to(UsersDto.class,usersEntity.get())));
            httpStatus = HttpStatus.OK;
        }else{
            result.put("desc","회원 가입 실패 [입력값이 잘못되었습니다]");
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
        }

        return new ResponseEntity(result,httpStatus);
    }
}
