package com.example.demo.chain;

import com.example.demo.repository.WalletRepository;
import com.example.demo.service.ChainService;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        public void init() {
            JsonParser jsonParser = new JsonParser();
            BufferedReader bufferedReaderBlock = null;
            BufferedReader bufferedReaderUTXOs = null;
            try {
                bufferedReaderBlock = Files.newBufferedReader(Paths.get("block.txt"));
                bufferedReaderUTXOs = Files.newBufferedReader(Paths.get("UTXOs.txt"));
                JsonArray blockArray = jsonParser.parse(bufferedReaderBlock.lines().collect(Collectors.joining(""))).getAsJsonArray();
                JsonObject UTXOsObject = jsonParser.parse(bufferedReaderUTXOs.lines().collect(Collectors.joining(""))).getAsJsonObject();
                initBlock(blockArray);
                initUTXOs(UTXOsObject);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String result  = "어떻게 나올까?";
        }
        public void destroy() throws Exception {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

            objectMapper.writeValue(new File("block.txt"),blockChain);
            objectMapper.writeValue(new File("UTXOs.txt"),UTXOs);
        }
    }
    public static void check(){
        int x = 0;
        int y = 0;
    }
    private void initUTXOs(JsonObject utxOsObject) {
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : utxOsObject.entrySet()) {
            String key = stringJsonElementEntry.getKey();
            TransactionOutput transactionOutput = initOutputs(stringJsonElementEntry.getValue().getAsJsonObject());
            UTXOs.put(key,transactionOutput);
        }
    }
    private static boolean genesis = true;
    public static ArrayList<Block> blockChain = new ArrayList<>();
    public static int difficulty = 3; //  채굴 난이도. hash에서 앞에 0이붙는 개수
    // 아직 사용되지 않은 transactionout들, 잔고개념으로 볼 수 있다.
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<>();

    // 임의의 지갑 두개
    public static Wallet adminWallet;

    public static float minimumTransaction = 0.1f; // 최소 전송 금액
    public static Transaction genesisTransaction; // 시작 트랜잭션

    public static void dostuffs() {

        // security provider로 bouncyCastle을 사용
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 지갑 생성하기
        // 지갑은 트랜잭션 결과와 잔액을 저장한다.
        String publ ="[48,73,48,19,6,7,42,-122,72,-50,61,2,1,6,8,42,-122,72,-50,61,3,1,1,3,50,0,4,-48,122,-121,-51,-107,113,91,34,105,32,79,73,-106,-36,0,-51,109,70,-108,-50,79,59,-15,95,-63,99,42,-59,-107,88,58,84,-114,-85,-86,-16,-125,7,-31,-98,-58,-35,-37,-107,22,-37,126,94]";
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(publ).getAsJsonArray();
        PublicKey adminPublicKey = Utils.toPublicKey(jsonArray);
        adminWallet = new Wallet();


        // 전체 물량을 생성할 최초의 지갑
        Wallet coinbase = new Wallet();

        //최초 트랜잭션 생성하기, 100NoobChain을 지급
        // 이외의 지급 조건이 없으므로 100Noob이 끝
        // 비트코인의 경우 새롭게 블록을 채굴하면 처음 50bit 부터 시작해서 4년마다 반감기를 거치면서 지급한다. 2021년 기준 블록 1개를 채굴하면 6.25bit 지급
        // 비트코인에서 새로운 채굴 난이도는 10분에 한개가 생성되도록 조정된다. 하루 2,102,000개 생성
//        genesisTransaction = new Transaction(coinbase.publicKey, adminWallet.publicKey, 100000000000f, null);
        genesisTransaction = new Transaction(coinbase.publicKey, adminPublicKey, 100000000000f, null);
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
        addBlock(block1);
        Block block2 = new Block(block1.hash);
        addBlock(block2);
        isChainValid();

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.

        Transaction genesis = blockChain.stream()
                .map(block ->
                        block.transactions.stream()
                                .filter(transaction ->{return transaction.transactionId.equals("0");
                }).findFirst().orElse(null))
                .findFirst()
                .get();
//        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        tempUTXOs.put(genesis.outputs.get(0).id, genesis.outputs.get(0));

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
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static void initBlock(JsonArray jsonArray){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String hash = jsonObject.get("hash").getAsString();
            String previousHash = jsonObject.get("previousHash").getAsString();
//            String merkleRoot = jsonObject.get("merkleRoot").getAsString();
            Long timeStamp = jsonObject.get("timeStamp").getAsLong();
            int nonce = jsonObject.get("nonce").getAsInt();
            JsonArray jsonTransactions = jsonObject.getAsJsonArray("transactions");

            ArrayList<Transaction> transactionList = initTransaction(jsonTransactions);

            blockChain.add(new Block(hash,previousHash,transactionList,timeStamp,nonce));
        }
    }

    private static ArrayList<Transaction> initTransaction(JsonArray jsonArray){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String transactionId = jsonObject.get("transactionId").getAsString();
            float value =jsonObject.get("value").getAsFloat();
            JsonArray from = jsonObject.get("from").getAsJsonArray();
            JsonArray to = jsonObject.get("to").getAsJsonArray();
            JsonArray signature = jsonObject.get("sign").getAsJsonArray();
            JsonArray inputs = jsonObject.get("inputs").getAsJsonArray();
            JsonArray outputs = jsonObject.get("outputs").getAsJsonArray();
            float outputsValue = jsonObject.get("outputsValue").getAsFloat();
            float inputsValue = jsonObject.get("inputsValue").getAsFloat();

            ArrayList<TransactionInput> inputArrayList = initInputs(inputs);
            ArrayList<TransactionOutput> outputArrayList = initOutputs(outputs);

            transactions.add(new Transaction(transactionId,Utils.toPublicKey(from),Utils.toPublicKey(to),value,inputArrayList,outputArrayList,signature));
        }
        return transactions;
    }

    private static ArrayList<TransactionOutput> initOutputs(JsonArray outputs) {
        ArrayList<TransactionOutput> transactionOutputs = new ArrayList<>();
        for (JsonElement jsonElement : outputs) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            transactionOutputs.add(initOutputs(jsonObject));
        }
        return transactionOutputs;
    }

    private static TransactionOutput initOutputs(JsonObject output) {

        String id = output.get("id").getAsString();
        float value = 0;
        String parentTransactionId = null;
        JsonArray recipe = null;
        PublicKey publicKey = null;

        try {
            value =output.get("value").getAsFloat();
            parentTransactionId = output.get("parentTransactionId").getAsString();
            if(parentTransactionId.equals("0")) { // 최초의 코인인지 확인한다.
                CookChain.genesis = false;
            }
            recipe = output.get("recipe").getAsJsonArray();
            publicKey = Utils.toPublicKey(recipe);
        }catch (Exception e){
            return null;
        }
        return new TransactionOutput(publicKey,value,parentTransactionId);
    }

    private static ArrayList<TransactionInput> initInputs(JsonArray inputs) {
        ArrayList<TransactionInput> transactionInputs = new ArrayList<>();
        for (JsonElement jsonElement : inputs) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String transactionOutputId = jsonObject.get("transactionOutputId").getAsString();
            JsonObject UTXO = jsonObject.getAsJsonObject("UTXO");

            transactionInputs.add(new TransactionInput(transactionOutputId,initOutputs(UTXO)));
        }
        return transactionInputs;
    }
}
