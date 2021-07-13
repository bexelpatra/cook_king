package com.example.demo.controller;

import com.example.demo.chain.CookChain;
import com.example.demo.chain.Transaction;
import com.example.demo.chain.Wallet;
import com.example.demo.dto.TransationDto;
import com.example.demo.entity.WalletEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.ChainService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.*;

@Controller
@RequestMapping("/chain")
@RequiredArgsConstructor
@CrossOrigin
public class CookChainController {
    private final UserService userService;
    private final WalletRepository walletRepository;
    private final ChainService chainService;

    @GetMapping(value = "/wallet")
    public ResponseEntity getOrMakeWallet(@RequestParam("token")String token){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        Optional<WalletEntity> keyEntity = walletRepository.findByUsersEntityId(usersEntity.getId());
        WalletEntity key = null;
        JsonParser jsonParser = new JsonParser();
        String pk = "";
        if(!keyEntity.isPresent()){
            key = chainService.makeWallet(usersEntity);
//            JsonArray publicKey = jsonParser.parse(key.getPublicKey()).getAsJsonArray();
            String x= chainService.makePublicKeyToQRCode(key.getPublicKey());
            result.put("desc","새롭게 지갑을 만들었습니다.");
            result.put("publicKey",x);

        }else{
            key = keyEntity.get();
            JsonArray publicKey = jsonParser.parse(key.getPublicKey()).getAsJsonArray();
            String x= chainService.makePublicKeyToQRCode(key.getPublicKey());
            result.put("desc","기존의 지갑에서 키를 받아왔습니다.");
            result.put("publicKey",x);
            String b64 = Base64.getEncoder().encodeToString(Utils.toPublicKey(publicKey).getEncoded());
            byte[] xy = Base64.getDecoder().decode(b64);
            PublicKey publicKey1 = Utils.toPublicKey(xy);
            byte[] xyz = publicKey1.getEncoded();
            String xasdf = " ";
        }
        try {
            pk = new String(key.getPublicKey().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("pk",pk);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/coin")
    public ResponseEntity sendCoin(
            @RequestParam("token")String token,
            @RequestParam("publicKey")String receiver,
            @RequestParam("value")float value){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        WalletEntity from = walletRepository.findByUsersEntityId(usersEntity.getId()).orElseThrow(() -> new RuntimeException("wallet not found"));
        WalletEntity to = walletRepository.findTopByPublicKey(receiver).orElseThrow(() -> new RuntimeException("wallet not found"));

        boolean response = chainService.send(from,to,value);
        if(response){
            result.put("desc",String.format("성공적으로 [%f]를 전송했습니다.",value));
        }else{
            result.put("desc","전송에 실패했습니다.");
        }
        return new ResponseEntity(result,httpStatus);
    }
    
    // 트랜잭션 검색 및 조회하기
    @GetMapping(value = "/transaction")
    public ResponseEntity getTransaction(
            @RequestParam(value = "from",required = false)String from,
            @RequestParam(value = "to",required = false)String to){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        List<Transaction> transactions = new ArrayList<>();
        CookChain.blockChain.stream().forEach(block -> transactions.addAll(block.transactions));

        List<TransationDto> transationDtos = TransationDto.convert(transactions);
        result.put("transactions", transationDtos);
        result.put("desc","성공적으로 조회했습니다.");
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
