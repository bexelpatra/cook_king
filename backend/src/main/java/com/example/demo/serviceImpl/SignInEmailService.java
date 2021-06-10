package com.example.demo.serviceImpl;

import com.example.demo.entity.EmailEntity;
import com.example.demo.repository.EmailRepository;
import com.example.demo.service.EmailService;
import com.example.demo.utilities.Utils;
import com.example.demo.utilities.SMTP;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInEmailService implements EmailService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final EmailRepository emailRepository;
    private final EntityManager entityManager;
    private final int certEXP = 1;
    private final int singUpEXP = 10;
    @Override
    public String sendCertMail(String receiver) {

        String number = Utils.getNumber(6);
        if(!Utils.isValidEmail(receiver)) return "이메일 주소가 잘못되었습니다.";
        if(alreadyHasCertMail(receiver)) return "이미 메일이 발송되었습니다.";

        boolean flag = SMTP.send(receiver,
                "[모두의 레시피] 인증번호 발송",
                String.format("안녕하세요 [모두의 레시피]입니다. \n 인증번호 : %s \n 위 6자리 번호를 입력해주세요. \n 유효기간 : %s"
                        ,number
                        ,Utils.dateToStr(Utils.getDate(Calendar.MINUTE, certEXP))));

        emailRepository.save(EmailEntity.builder()
                .email(receiver)
                .number(number)
                .regDate(new Date())
                .check(false)
                .build());

        return flag ? null:"이메일 발송에 실패했습니다.";
    }

    /**
     * 3분내로 입력해야 인증이 된다.
     * @param receiver 이메일 주소
     * @param number 인증 번호
     * @return
     */
    @Override
    public boolean certificate(String receiver, String number) {
        Optional<EmailEntity> emailEntity = emailRepository.findTopByEmailAndNumberAndRegDateIsAfterOrderByRegDateDesc(receiver,number,Utils.getDate(Calendar.MINUTE,-3));
        if(!emailEntity.isPresent()){
            return false;
        }else {
            emailEntity.get().setCheck(true);
            return true;
        }
    }

    /**
     * 가장 최근에 발송한 이메일이
     * 1. 10분 이내에 발송되었고
     * 2. 체크되었는지 확인한다.
     *
     *  true면 회원가입 진행, 아니면 재인증
     * @param receiver
     * @return
     */
    @Override
    public boolean check(String receiver) {
        boolean hasChecked = emailRepository.findTopByEmailAndRegDateIsAfterOrderByRegDateDesc(receiver,Utils.getDate(Calendar.MINUTE,-singUpEXP))
                .filter(emailEntity -> emailEntity.isCheck())
                .isPresent();
//        String query = new Querying(EmailEntity.class)
//                .add("email","=",receiver)
//                .end(Querying.Sort.DESC,"id");
//        List<EmailEntity> xx = entityManager.createNativeQuery(query,EmailEntity.class).getResultList();
//        List<EmailEntity> emailEntities = xx.stream().filter(emailEntity -> emailEntity.getRegDate().after(calendar.getTime())).collect(Collectors.toList());
//        boolean xxxx = xx.stream().allMatch(emailEntity -> emailEntity.getRegDate().after(Utils.getDate(Calendar.MINUTE,-10)));
        return hasChecked;
    }

    public boolean alreadyHasCertMail(String receiver){
        if(emailRepository.findTopByEmailAndRegDateIsAfterOrderByRegDateDesc(receiver, Utils.getDate(Calendar.MINUTE,-certEXP)).isPresent()){
            return true;
        }else {
            emailRepository.deleteEmail(receiver,Utils.dateToStr(new Date()));
            return false;
        }
    }

}
