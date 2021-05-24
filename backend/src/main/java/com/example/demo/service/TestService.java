package com.example.demo.service;

import com.example.demo.entity.UsersEntity;
import org.springframework.stereotype.Service;

@Service
public interface TestService {
    UsersEntity getUserListById(int id);
}
