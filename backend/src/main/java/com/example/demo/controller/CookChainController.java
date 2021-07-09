package com.example.demo.controller;

import com.example.demo.chain.CookChain;
import com.example.demo.chain.Transaction;
import com.example.demo.chain.Wallet;
import com.example.demo.dto.TransationDto;
import com.example.demo.entity.KeyEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.KeyRepository;
import com.example.demo.service.ChainService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/chain")
@RequiredArgsConstructor
@CrossOrigin
public class CookChainController {
    private final UserService userService;
    private final KeyRepository keyRepository;
    private final ChainService chainService;

    @GetMapping(value = "/wallet")
    public ResponseEntity getOrMakeWallet(@RequestParam("token")String token){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        Optional<KeyEntity> keyEntity = keyRepository.findByUsersEntityId(usersEntity.getId());
        KeyEntity key = null;

        if(!keyEntity.isPresent()){
            key = chainService.makeWallet(usersEntity);
            result.put("desc","새롭게 지갑을 만들었습니다.");
        }else{
            key = keyEntity.get();
            result.put("desc","기존의 지갑에서 키를 받아왔습니다.");
        }

        result.put("publicKey",key.getPublicKey());
        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/coin")
    public ResponseEntity sendCoin(@RequestParam("token")String token,@RequestParam("publicKey")String receiver,@RequestParam("value")float value){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        UsersEntity usersEntity = userService.findUsersEntityByToken(token).orElseThrow(() -> new RuntimeException("user not found"));
        KeyEntity from = keyRepository.findByUsersEntityId(usersEntity.getId()).orElseThrow(() -> new RuntimeException("wallet not found"));
        KeyEntity to = keyRepository.findTopByPublicKey(receiver).orElseThrow(() -> new RuntimeException("wallet not found"));

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
    public ResponseEntity getTransaction(@RequestParam(value = "from",required = false)String from,@RequestParam(value = "to",required = false)String to){
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
