package com.example.demo.utilities;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

@Service
public class SMTP {
    public static boolean send(String receiver,String subject,String text){
        final String user = "coookkingofficial@gmail.com";
        final String pw = "dyfldhkd0$";

        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){ return new PasswordAuthentication(user,pw); }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(user));
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));

            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);

            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 메일 여러명에게 보내기
     * @param receiver 수신자 목록
     * @param subject 제목
     * @param text 내용
     */
    public static boolean send(String[] receiver,String subject,String text){
        final String user = "coookkingofficial@gmail.com";
        final String pw = "dyfldhkd0$";

        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){ return new PasswordAuthentication(user,pw); }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(user));

            InternetAddress[] receivers = (InternetAddress[]) Arrays.stream(receiver).map(s -> {
                        try {
                            return new InternetAddress(s);
                        } catch (AddressException e) {
                            return null;
                        }
                    }).toArray();

            mimeMessage.addRecipients(Message.RecipientType.TO,receivers);

            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);

            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
