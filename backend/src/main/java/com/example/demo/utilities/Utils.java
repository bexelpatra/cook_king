package com.example.demo.utilities;

import com.example.demo.testing.MyEnum;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class Utils {
    static ObjectMapper objectMapper = new ObjectMapper();
    static Random random=new Random();
    public Utils() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    // 제네릭 메소드 연습차원으로 만들어본 내용들
    public static  <T extends MyEnum> int getValue(T t){
        return t.getValue();
    }

    // 제네릭 메소드 연습차원으로 만들어본 내용들
    public static <T extends MyEnum> String getDesc(T t){
        return t.getDesc();
    }

    // 제네릭 메소드 연습차원으로 만들어본 내용들
    public static <T extends MyEnum> T byValue(Class<T> t,int val){
        MyEnum[] ts = t.getEnumConstants();
        for (MyEnum myEnum : ts) {
            if(myEnum.getValue() == val) return (T) myEnum;
        }
        return null;
    }



    // dto -> entity, entity -> dto converter
    public static<F,T> T to(Class<T> to, F from){
        return objectMapper.convertValue(from,to);
    }
    public static<F,T> List<T> to(Class<T> to, List<F> froms){
        List<T> ts = new ArrayList<>();
        for (F f : froms) {
            T convertValue =objectMapper.convertValue(f,to);
            ts.add(convertValue);
        }
        return ts;
    }

    public static String getNumber(int len){
        if(len<=0) len = 1;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<len;i++){
            builder.append(random.nextInt(9));
        }
        return builder.toString();
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) { err = true; }
        return err;
    }

    public static String dateToStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String dateToStr(int field,int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(field,amount);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }

    public static Date getDate(int field,int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(field,amount);
        return calendar.getTime();
    }
}
