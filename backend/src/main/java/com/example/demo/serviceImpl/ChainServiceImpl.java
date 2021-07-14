package com.example.demo.serviceImpl;

import com.example.demo.chain.Block;
import com.example.demo.chain.CookChain;
import com.example.demo.chain.Wallet;
import com.example.demo.entity.WalletEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.ChainService;
import com.example.demo.utilities.Utils;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ChainServiceImpl implements ChainService {

    private final WalletRepository walletRepository;
    @Override
    public String makePublicKeyToQRCode(String publicKey) {
        if(publicKey == null || publicKey.equals("")) return null;
        BitMatrix bitMatrix=null;
        MatrixToImageConfig matrixToImageConfig = null;

        String imageToStr = "";
        // 큐알코드 바코드 및 배경 색상값
        int qrcodeColor =   0xFF000000; // 검정색
        int backgroundColor = 0xFFFFFFFF; // 하얀색
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);

        // QRCode 전체 크기
        int width=100;
        int height=100;

        try {
            bitMatrix = qrCodeWriter.encode(publicKey, BarcodeFormat.QR_CODE,width, height); //단위는 픽셀이다.
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, matrixToImageConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // image를 binaray로 변환한다.
        imageToStr = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        return imageToStr;
    }

    @Override
    public WalletEntity makeWallet(UsersEntity usersEntity) {
        if(usersEntity == null) return null;
        Wallet wallet = new Wallet();
        return walletRepository.save(new WalletEntity(join(wallet.publicKey.getEncoded()),join(wallet.privateKey.getEncoded()),usersEntity));
    }

    @Override
    public boolean send(WalletEntity from, WalletEntity to, float value) {
        JsonParser jsonParser = new JsonParser();
        byte[] fromPublicKey = Utils.toBytes(jsonParser.parse(from.getPublicKey()).getAsJsonArray());
        byte[] fromPrivateKey = Utils.toBytes(jsonParser.parse(from.getPrivateKey()).getAsJsonArray());

        byte[] toPublicKey = Utils.toBytes(jsonParser.parse(to.getPublicKey()).getAsJsonArray());

        Wallet sender = new Wallet(fromPrivateKey,fromPublicKey);

        Block block = CookChain.blockChain.get(CookChain.blockChain.size()-1);
        if(block.transactions.size()>=1000){
            CookChain.addBlock(new Block(block.hash));
        }

        return CookChain.blockChain.get(CookChain.blockChain.size()-1).addTransaction(sender.sendFunds(Utils.toPublicKey(toPublicKey),value));
    }

    private String join(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (byte aByte : bytes) {
            builder.append(aByte);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }
}
