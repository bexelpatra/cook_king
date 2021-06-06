package com.example.demo.testing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public abstract class UTILS {
    static ObjectMapper objectMapper = new ObjectMapper();
    public UTILS() {
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
}
