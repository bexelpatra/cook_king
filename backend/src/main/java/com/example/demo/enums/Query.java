package com.example.demo.enums;

public enum Query {
    EQUAL(0," = "),
    GREATERTHAN(1," > "),
    GREATERTHANEQUAL(2," >= "),
    LESSTHAN(3,"<"),
    LESSTHANEQUAL(4,"=<"),
    ;

    private int value;
    private String desc;

    Query(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() { return value; }
    public String getDesc() { return desc; }
}
