package com.example.demo.dto;

import com.example.demo.chain.Transaction;
import com.example.demo.chain.TransactionInput;
import com.example.demo.chain.TransactionOutput;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bouncycastle.jce.provider.symmetric.ARC4;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TransationDto {

    public String transactionId;// transaction의 hash
//    @JsonIgnore
//    public PublicKey sender; // 보내는 사람의 공개키
//    @JsonIgnore
//    public PublicKey recipient; // 받는 사람의 공개키
    private List<Byte> from = new ArrayList<>();
    private List<Byte> to = new ArrayList<>();
    private List<Byte> sign = new ArrayList<>();

    private String f;
    private String t;
    private String s;

    public float value; // 보내는 금액
    @JsonIgnore
    public byte[] signature;// 보내는 사람의 전자 서명

    public ArrayList<TransactionInput> inputs = new ArrayList<>(); // 보내는 사람의 지갑에 있는 트랜잭션에서 UTXOs를 입력값으로 사용한다. ex)13200원을 보내려면 만원 1장, 천원 3장, 백원 2개
    public ArrayList<TransactionOutput> outputs = new ArrayList<>(); // 트랜잭션을 입력해서 나오는 결과 트랜잭션. 수신자, 채굴자, 본인(거스름돈같은 개념)에게 주는 등 다양한 output이 나올 수 있다.

    public List<String> inputList = new ArrayList<>();
    public List<String> outputList = new ArrayList<>();

    private static int sequence =0;

    public static TransationDto convert(Transaction transaction){
        TransationDto transationDto = new TransationDto();
        transationDto.setTransactionId(transaction.transactionId);
        transationDto.setValue(transaction.value);
        transationDto.setOutputList(transaction.outputs.stream().map(transactionOutput -> transactionOutput.id).collect(Collectors.toList()));
        transationDto.setInputList(transaction.inputs.stream().map(transactionInput -> transactionInput.transactionOutputId).collect(Collectors.toList()));
        transationDto.setF(Base64.getEncoder().encodeToString(transaction.sender.getEncoded()));
        transationDto.setT(Base64.getEncoder().encodeToString(transaction.recipient.getEncoded()));
        transationDto.setS(Base64.getEncoder().encodeToString(transaction.signature));

        return transationDto;
    }
    public static List<TransationDto> convert(List<Transaction> transactions){
        List<TransationDto> transationDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transationDtos.add(convert(transaction));
        }
        return transationDtos;
    }
}
