package com.example.demo.service;

import com.example.demo.entity.WalletEntity;
import com.example.demo.entity.UsersEntity;

public interface ChainService {
    String makePublicKeyToQRCode(String publicKey);
    WalletEntity makeWallet(UsersEntity usersEntity);

    boolean send(WalletEntity from, WalletEntity to, float value);
}
