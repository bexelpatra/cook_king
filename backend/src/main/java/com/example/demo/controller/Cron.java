package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
@Controller
public class Cron {
    private static final Logger logger = LoggerFactory.getLogger(Cron.class);
    // n분에 한번씩 이메일인증들을 지워줘야 한다.
    @Scheduled(cron="0/3 * * * * 1")//3초에 한번
    public String refreshEmail(){
        return "";
    }
}
