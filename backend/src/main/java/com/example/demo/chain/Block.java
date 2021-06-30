package com.example.demo.chain;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
//    private String data;
    private long timeStamp;
    private int nonce;

    //constructor
//    public Block(String data, String previousHash) {
//        this.previousHash = previousHash;
//        this.data = data;
//        this.timeStamp = new Date().getTime();
//        this.hash = calculateHash();
//    }

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public Block(String hash, String previousHash, ArrayList<Transaction> transactions, long timeStamp, int nonce) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.merkleRoot = StringUtil.getMerkleRoot(transactions);
    }

    public String calculateHash(){
        String calculatehash = StringUtil.applySha256(
                previousHash
                        +Long.toString(timeStamp)
                        +Integer.toString(nonce)
//                        +data
        );
        return calculatehash;
    }

    public void mineBlock(int difficulty){
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0','0');

        while(!hash.substring(0,difficulty).equals(target)){
            nonce++;

            hash = calculateHash();
        }
        System.out.println("Block Mined !!! : " + hash);
    }

    public boolean addTransaction(Transaction transaction){
        if(transaction == null) return false;
        if(previousHash != "0"){
            if(transaction.processTransaction() != true){
                System.out.println("Transaction failed to process. Discarded");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Succesfully added to Block");
        return true;
    }

    public long getTimeStamp() { return timeStamp; }
    public int getNonce() { return nonce; }
}
