package com.example.demo.enums;

public enum ContentKind {
    IMAGE(0,"이미지","5번버스"),
    VIDEO(1,"영상","1번 버스"),
    TITLE(2,"메인","5633버스"),
    ;

    private int value;
    private String desc;
    private String bus;

    ContentKind(int value, String desc, String bus) {
        this.value = value;
        this.desc = desc;
        this.bus = bus;
    }

    public int getValue() { return value; }
    public String getDesc() { return desc; }
    public String getBus() { return bus; }

}
