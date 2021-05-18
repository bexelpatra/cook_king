package com.example.demo.controller;

import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "test")
@RequiredArgsConstructor
public class TestController {
    private final UsersRepository usersRepository;

    @GetMapping(value = "/test1")
    public ResponseEntity test1(){
        Map<String,Object> result = new HashMap<>();
        UsersEntity usersEntity = new UsersEntity();
        List<UsersEntity> usersEntities = usersRepository.findUsersEntitiesByIdGreaterThanEqual(1);

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
