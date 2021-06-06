package com.example.demo.utilities;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Data
public class Querying<X> {
    private Class<X> x;
    private StringBuilder result;

    private String name;
    private String sign;
    private String value;
    public enum Sort{
        ASC(0," "),
        DESC(1, "desc")
        ;
        private int value;
        private String desc;

        Sort(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() { return value; }public String getDesc() { return desc; }
    }
    public Querying(Class<X> x) {
        this.result = new StringBuilder();
        this.x = x;
        result.append("where ");
    }

    public Querying add(String name, String sign,String value){
        try {
            x.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("필드값이 없습니다.");
        }

        result.append("a."+name+" ");
        result.append(sign+" ");
        result.append(String.format("\'%s\' ",value));
        result.append("and ");
        return this;
    }


    public String end(){
        return this.result.substring(0,result.length()-5);
    }
    public String end(Sort sort,String filed){
        try {
            x.getDeclaredField(filed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("필드값이 없습니다.");
        }
        String sorting = String.format(" order by %s %s",filed,sort.getDesc());
        return (this.result.substring(0,result.length()-5) + sorting);
    }
}
