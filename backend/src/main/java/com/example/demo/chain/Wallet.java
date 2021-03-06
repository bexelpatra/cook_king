package com.example.demo.chain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    @JsonIgnore
    public PrivateKey privateKey;
    @JsonIgnore
    public PublicKey publicKey;
    private byte[] privatekey;
    private byte[] publickey;

    public HashMap<String,TransactionOutput> UTXOs = new HashMap<>();

    /**
     * 생성자 : private key와 public key를 생성한다.
     *          이때 private key는 TX(transation)를 만들때 서명에 쓰이는 key로 다른 사람들에게 공개되면 안된다. 내가 지갑주인이라는 입증하는 key
     */
    public Wallet(){
        generateKeyPair();
    }

    // key 생성하기
    public void generateKeyPair(){
        try {
            //
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyPairGenerator.initialize(ecSpec,random);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

            this.privatekey = privateKey.getEncoded();
            this.publickey = publicKey.getEncoded();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public float getBalance(){
        float total = 0;
        for(Map.Entry<String,TransactionOutput> item : CookChain.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey)){
                UTXOs.put(UTXO.id,UTXO);
                total += UTXO.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey _recipient,float value){
        if(getBalance() < value){
            System.out.println("#Not Enough funds to send transaction. Transaction discarded");
            return null;
        }
        ArrayList<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }

    public Wallet(byte[] privateKey,byte[] publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA","BC");
            this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
            this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
