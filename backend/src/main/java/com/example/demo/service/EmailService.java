package com.example.demo.service;

public interface EmailService {
    boolean sendCertMail(String receiver);
    boolean checkMail(String receiver, String number);
    boolean check(String receiver);
}
