package com.example.demo.serviceImpl;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.EmailRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.TestService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sun.util.calendar.BaseCalendar;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final UsersRepository usersRepository;
    private final EmailRepository emailRepository;
    @Override
    public UsersEntity getUserListById(int id) {
        //long > int > short > byte
        usersRepository.findById((long)id);

        return null;
    }

    @Override
    public void test1(String email) {
        String x = Utils.dateToStr(new Date());
        emailRepository.deleteEmail(email, x);
    }

    @Override
    public void test2(String email) {
        List<EmailEntity> emailEntityList = emailRepository.findAllByEmail(email);
        emailRepository.deleteAll(emailEntityList);
    }
}
