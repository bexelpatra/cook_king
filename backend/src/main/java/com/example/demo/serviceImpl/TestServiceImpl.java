package com.example.demo.serviceImpl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final UsersRepository usersRepository;

    @Override
    public UsersEntity getUserListById(int id) {
        //long > int > short > byte
        usersRepository.findById((long)id);

        return null;
    }
}
