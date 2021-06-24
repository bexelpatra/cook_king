package com.example.demo.serviceImpl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.service.ChainService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChainServiceImpl implements ChainService {
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
    public String makeWallet(UsersEntity usersEntity) {
        return null;
    }
}
