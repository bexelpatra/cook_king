package com.example.demo.enums;

public enum FirstCategoryKind {
    KOREA(0,"한식"),
    JAPAN(1,"일식"),
    CHINA(2,"중식"),
    WESTERN(3,"양식"),
    ;

    private int value;
    private String desc;

    FirstCategoryKind(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() { return value; }
    public String getDesc() { return desc; }

    public static FirstCategoryKind byValue(int val){
        for (FirstCategoryKind value : values()) {
            if(value.getValue()== val) return value;
        }
        return null;
    }
}
