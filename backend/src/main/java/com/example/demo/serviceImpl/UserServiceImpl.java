package com.example.demo.serviceImpl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    @Override
    public Optional<UsersEntity> findUsersEntityByToken(String token) {
        return usersRepository.findUsersEntityByToken(token);
    }
}
