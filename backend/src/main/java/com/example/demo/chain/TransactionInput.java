package com.example.demo.chain;

public class TransactionInput {

    public String transactionOutputId;
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }

    @Override
    public String toString() {
        return "TransactionInput{" +
                "transactionOutputId='" + transactionOutputId + '\'' +
                ", UTXO=" + UTXO.toString() +
                '}';
    }
}
