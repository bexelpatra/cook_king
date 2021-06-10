package com.example.demo.service;

import org.springframework.context.annotation.Bean;

public interface EmailService {
    String sendCertMail(String receiver);
    boolean certificate(String receiver, String number);
    boolean check(String receiver);


}
