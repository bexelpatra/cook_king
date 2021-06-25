package com.example.demo.controller;

import com.example.demo.chain.Block;
import com.example.demo.chain.CookChain;
import com.example.demo.chain.Transaction;
import com.example.demo.chain.Wallet;
import com.example.demo.dto.StringDto;
import com.example.demo.dto.InnerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/test2")
public class TestController2 {

    @GetMapping("/test")
    public ResponseEntity responseEntity(){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        StringDto  stringDto = new StringDto();
        stringDto.setS("테스트");
        stringDto.setS1("에스원");
        stringDto.setS2("에스투");
        stringDto.setInnerDto(new InnerDto().setS1("testDto3"));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        stringDto.setInts(integerList);
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "";
        try {
            json = objectMapper.writeValueAsString(stringDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            String key = stringJsonElementEntry.getKey();
            JsonElement jsonElement1 = stringJsonElementEntry.getValue();
        }

        return new ResponseEntity(result,httpStatus);
    }

    @GetMapping(value = "/send")
    public ResponseEntity chainSend(){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
//        Block block = new Block(CookChain.blockChain.get(CookChain.blockChain.size()-1).hash);
//        block.addTransaction(new Transaction(CookChain.walletA.publicKey,CookChain.walletB.publicKey,1f,null));
//        CookChain.addBlock(block);
        CookChain.dostuffs();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
