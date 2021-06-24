package com.example.demo.service;

import com.example.demo.entity.UsersEntity;

public interface ChainService {
    String makePublicKeyToQRCode(String publicKey);
    String makeWallet(UsersEntity usersEntity);
}
