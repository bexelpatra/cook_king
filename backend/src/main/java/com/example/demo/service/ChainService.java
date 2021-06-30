package com.example.demo.service;

import com.example.demo.entity.KeyEntity;
import com.example.demo.entity.UsersEntity;

public interface ChainService {
    String makePublicKeyToQRCode(String publicKey);
    KeyEntity makeWallet(UsersEntity usersEntity);

    boolean send(KeyEntity from,KeyEntity to,float value);
}
