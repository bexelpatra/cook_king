package com.example.demo.utilities;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 기존 SMTP가 안되서 구글링해본 내용, 똑같은데 이녀석이 되고나니 STMP도 된다.
 */
public class MyMail {
    @Deprecated
    public static void gmailMailSend(String email, String subject, String text) {
        String host = "smtp.gmail.com";
        String user = "coookkingofficial@gmail.com";
        String sender = "coookkingofficial@gmail.com";
        String password = "";

        // SMTP 서버 정보를 설정한다. (ssl적용에따라 설정옵션이 달라진다. 아래는 ssl적용 안한버전이다.)
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");


        //인증
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            //받는사람 메일
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // 메일 제목
            message.setSubject(subject);
            // 메일 내용
            message.setText(text);
            // send the message
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
