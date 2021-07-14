package com.example.demo.chain;

import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TransactionOutput {
    public String id;
    @JsonIgnore
    public PublicKey recipient;
    private List<Byte> recipe = new ArrayList<>();
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey recipient,float value, String parentTransactionId) {
        this.recipient = recipient;
        for(byte b : recipient.getEncoded()){this.recipe.add(b);}
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient)+ Float.toString(value)+parentTransactionId);
    }

    public List<Byte> getRecipe() { return recipe; }public void setRecipe(List<Byte> recipe) { this.recipe = recipe; }

    public boolean isMine(PublicKey publicKey){
        for(int i=0;i<publicKey.getEncoded().length;i++){
            if(publicKey.getEncoded()[i]!=recipient.getEncoded()[i]) {
                return false;
            }
        }
//        return (publicKey == recipient);
        return true;
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
