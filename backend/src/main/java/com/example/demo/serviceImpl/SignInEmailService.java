package com.example.demo.serviceImpl;

import com.example.demo.entity.EmailEntity;
import com.example.demo.repository.EmailRepository;
import com.example.demo.service.EmailService;
import com.example.demo.utilities.Querying;
import com.example.demo.utilities.Utils;
import com.example.demo.utils.SMTP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInEmailService implements EmailService {

    private final EmailRepository emailRepository;
    private final EntityManager entityManager;
    @Override
    public boolean sendCertMail(String receiver) {

        String number = Utils.getNumber(6);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-3);

        boolean flag = SMTP.send(receiver,"[모두의 레시피] 인증번호 발송",String.format("안녕하세요 [모두의 레시피]입니다.<br>인증번호 : %s<br> 위 6자리 번호를 입력해주세요. <br> 유효기간 : %tF",number,calendar.getTime()));

        emailRepository.save(EmailEntity.builder()
                .email(receiver)
                .number(number)
                .regDate(new Date())
                .build());

        return flag;
    }

    @Override
    public boolean checkMail(String receiver, String number) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-3);
        Optional<EmailEntity> emailEntity = emailRepository.findTopByEmailAndNumberAndRegDateIsAfterOrderByRegDateDesc(receiver,number,calendar.getTime());
        if(!emailEntity.isPresent()){
            return false;
        }else {
            emailEntity.get().setCheck(true);
            return true;
        }
    }

    @Override
    public boolean check(String receiver) {
        String query = new Querying(EmailEntity.class)
                .add("email","=",receiver)
                .end(Querying.Sort.DESC,"id");
        String x = String.format("select * from email as a %s ",query);
//        boolean flag = emailRepository.getOneBy(x).map(emailEntity -> emailEntity.isCheck()).orElse(false);
        List<EmailEntity> o = entityManager.createNativeQuery(x,EmailEntity.class).getResultList();
        return false;
    }
}
