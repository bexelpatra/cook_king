package com.example.demo.enums;

import com.example.demo.testing.MyEnum;

public enum PinKind implements MyEnum<PinKind,Integer> {
    NOT_USING(0,"사용안함"),
    PIN(1,"핀비밀번호"),
    BIO_FINGER_PRINT(2,"지문 인식"),
    ;

    private int value;
    private String desc;
    private String name;

    PinKind(int value, String desc) {
        this.value = value;
        this.desc = desc;
        this.name = name;
    }

    public int getValue() { return value; }
    public String getDesc() { return desc; }

    @Override
    public PinKind of(int val) {
        for (PinKind pinKind : values()) {
            if(pinKind.getValue() == val) return pinKind;
        }
        return  null;
    }

//    public static PinKind byValue(int val){
//        for (PinKind pinKind : values()) {
//            if(pinKind.getValue() == val) return pinKind;
//        }
//        return null;
//    }

}
