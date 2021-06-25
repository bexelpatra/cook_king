package com.example.demo.chain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    @JsonIgnore
    public PublicKey recipient;
    private byte[] recipe;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey recipient,float value, String parentTransactionId) {
        this.recipient = recipient;
        this.recipe = recipient.getEncoded();
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient)+ Float.toString(value)+parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey){
        return (publicKey == recipient);
    }

    @Override
    public String toString() {
        return "TransactionOutput{" +
                "id='" + id + '\'' +
                ", recipient=" + recipient +
                ", value=" + value +
                ", parentTransactionId='" + parentTransactionId + '\'' +
                '}';
    }
}
