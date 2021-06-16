package com.example.demo.enums;

public enum SecondCategoryKind {
    ETC(0,"기타"),
    FRY_STIR(1,"볶음"),
    FRY_DEEP(2,"튀김"),
    GRILL(3,"구이"),
    STEAM(4,"찜"),
    SOUP(5,"국물"),
    ;

    private int value;
    private String desc;

    SecondCategoryKind(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() { return value; }public String getDesc() { return desc; }

    public static SecondCategoryKind byValue(int val){
        for (SecondCategoryKind secondCategoryKind : values()) {
            if(secondCategoryKind.getValue()==val) return secondCategoryKind;
        }
        return null;
    }
    public static SecondCategoryKind[] byValue(int[] values){
        int len = values.length;
        SecondCategoryKind[] secondCategoryKinds = new SecondCategoryKind[len];
        for(int i=0; i<len;i++){
            secondCategoryKinds[i] = byValue(values[i]);
            if(byValue(values[i]) == null) return null;
        }
        return secondCategoryKinds;
    }
}
