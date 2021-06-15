package com.example.demo.enums;

public enum ContentKind {
    IMAGE(0,"이미지"),
    VIDEO(1,"영상"),
    TITLE(2,"메인"),
    ;

    private int value;
    private String desc;

    ContentKind(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() { return value; }
    public String getDesc() { return desc; }

    public static ContentKind byValue(int val){
        for (ContentKind contentKind : values()) {
            if(contentKind.getValue() == val) return contentKind;
        }
        return null;
    }

}
