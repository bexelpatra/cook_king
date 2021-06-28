package com.example.demo.chain;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    참고자료 : https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa

    참고자료라기 보다는 필사하는 느낌으로 공부해봤다.

    1. 블록체인을 만들고 data를 저장한다.
    2. 전자서명을 통해 블록을 연결한다.
    3. pow 알고리즘을 이용해서 새로운 블록의 타당성을 검증한다.

 */
@Configuration
public class CookChain {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Coin coin(){ return new Coin(); }
    public class Coin{
        public void init() throws Exception {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(""));
//            bufferedReader.lines().map(s -> {
//                String[] infos = s.split("#");
//                new Block(1,1,1,1,1);
//            })
        }
        public void destroy()throws Exception {
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(""));
        }
    }

    public static ArrayList<Block> blockChain = new ArrayList<>();
    public static int difficulty = 3; //  채굴 난이도. hash에서 앞에 0이붙는 개수
    // 아직 사용되지 않은 transactionout들, 잔고개념으로 볼 수 있다.
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<>();

    // 임의의 지갑 두개
    public static Wallet walletA;
    public static Wallet walletB;

    public static float minimumTransaction = 0.1f; // 최소 전송 금액
    public static Transaction genesisTransaction; // 시작 트랜잭션
    public static void dostuffs() {

        // security provider로 bouncyCastle을 사용
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 지갑 생성하기
        // 지갑은 트랜잭션 결과와 잔액을 저장한다.
        walletA = new Wallet();
        walletB = new Wallet();
        Wallet coinbase = new Wallet();

        //최초 트랜잭션 생성하기, 100NoobChain을 지급
        // 이외의 지급 조건이 없으므로 100Noob이 끝
        // 비트코인의 경우 새롭게 블록을 채굴하면 처음 50bit 부터 시작해서 4년마다 반감기를 거치면서 지급한다. 2021년 기준 블록 1개를 채굴하면 6.25bit 지급
        // 비트코인에서 새로운 채굴 난이도는 10분에 한개가 생성되도록 조정된다. 하루 2,102,000개 생성
        genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
        genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);

        //testing
        Block block1 = new Block(genesis.hash);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
        block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
        addBlock(block1);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

        Block block2 = new Block(block1.hash);
        System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
        block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
        addBlock(block2);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

//        Block block3 = new Block(block2.hash);
//        System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
//        block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20));
//        block3.addTransaction(walletB.sendFunds( walletA.publicKey, 1));
//        block3.addTransaction(walletB.sendFunds( walletA.publicKey, 2));
//        block3.addTransaction(walletB.sendFunds( walletA.publicKey, 3));
//        block3.addTransaction(walletB.sendFunds( walletA.publicKey, 4));
//        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
//        System.out.println("WalletB's balance is: " + walletB.getBalance());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
        String x= "";
        JsonParser jsonParser = new JsonParser();
        try {
            objectMapper.writeValue(new File("im.txt"),blockChain);
            x = objectMapper.writeValueAsString(blockChain);
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("im.txt"));
            JsonArray jsonArray = jsonParser.parse(bufferedReader.lines().collect(Collectors.joining(""))).getAsJsonArray();
            initConverter(jsonArray);
            jsonArray.forEach(jsonElement -> jsonElement.getAsJsonObject().entrySet().stream().forEach(stringJsonElementEntry -> System.out.println(stringJsonElementEntry.getKey()+":"+stringJsonElementEntry.getValue())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isChainValid();

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        //loop through blockchain to check hashes:
        for(int i=1; i < blockChain.size(); i++) {

            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("#Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            //loop thru blockchains transactions:
            TransactionOutput tempOutput;
            for(int t=0; t <currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if(!currentTransaction.verifySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
                    return false;
                }

                for(TransactionInput input: currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if(tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }

                    if(input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for(TransactionOutput output: currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }

                if( currentTransaction.outputs.get(0).recipient != currentTransaction.recipient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if( currentTransaction.outputs.get(1).recipient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockChain.add(newBlock);
    }

    private static void initBlock(Block newBlock) {
        blockChain.add(newBlock);
    }
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static void initConverter(JsonArray jsonArray){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

//        jsonArray.forEach(jsonElement -> initBlock(new Block()));
        jsonArray.forEach(jsonElement -> System.out.println(jsonElement.getAsJsonObject().get("transactions")));
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String hash = jsonObject.get("hash").getAsString();
            String previousHash = jsonObject.get("previousHash").getAsString();
            String merkleRoot = jsonObject.get("merkleRoot").getAsString();
            Date timeStamp = new Date(jsonObject.get("timeStamp").getAsLong());
            int nonce = jsonObject.get("nonce").getAsInt();
            JsonArray array = jsonObject.getAsJsonArray("transactions");
            String x= "";
        }
    }

    private void initTransaction(JsonArray jsonArray){
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int transactionId = jsonObject.get("transactionId").getAsInt();
            float value =jsonObject.get("value").getAsFloat();
//            String from = jsonObject.get("")
        }
    }
}
