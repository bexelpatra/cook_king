package com.example.demo.utilities;

import lombok.Data;

/**
 * nativeQuery 만들기
 * join 기능은 할수 없다.
 * @param <X>
 */
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
        String table = x.getName().toLowerCase();
        result.append(String.format("select * from %s where ",table.substring(table.lastIndexOf(".")+1,table.lastIndexOf("entity"))));
    }

    public Querying add(String name, String sign,String value){
        try {
            x.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("필드값이 없습니다.");
        }

        result.append(name+" ");
        result.append(sign+" ");
        result.append(String.format("\'%s\' ",value));
        result.append("and ");
        return this;
    }


    public String end(){
        return this.result.substring(0,result.length()-5);
    }
    public String end(Sort sort,String field){
        try {
            x.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("필드값이 없습니다.");
        }
        String sorting = String.format(" order by %s %s",field,sort.getDesc());
        return (this.result.substring(0,result.lastIndexOf("and")) + sorting);
    }
}
