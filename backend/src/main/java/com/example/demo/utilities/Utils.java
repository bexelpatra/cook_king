package com.example.demo.utilities;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.testing.MyEnum;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class Utils<T> {
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

    private static Optional<Class> findOpponent(Class[] classes,String entityName){
        entityName = entityName.toLowerCase().substring(entityName.lastIndexOf(".")+1).replace("entity","");

        String finalEntityName = entityName;
        for (Class aClass : classes) {
            aClass.getName().toLowerCase();
        }
        return Arrays.stream(classes).filter(aClass -> {
            System.out.println(aClass.getName().toLowerCase());
            return aClass.getName().toLowerCase().contains(finalEntityName+"dto");
        }).findFirst();
    }
    private static<T,R> Optional<R> invokeGetter(Class<T> t,Field field){ // 부모 클래스, 필드의 클래스
        Optional<Method> optional = Arrays.stream(t.getDeclaredMethods())
                .filter(method -> method.getName().toLowerCase().contains(field.getName().toLowerCase()))
                .findFirst();
        Object o = null;
        try {
            if(optional.isPresent()){
                o = (optional.get().invoke(t, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // dto -> entity, entity -> dto converter
    public static<F,T> T test(Class<T> to, F from){
        Field[] fields=to.getDeclaredFields();
        for (Field field : fields) {
            // not recursive...
            if(field.getName().endsWith("Entity")){
                Class target = field.getType();
                Optional<Class> opponent = findOpponent(to.getDeclaredClasses(),target.getName());
                Object o = null;
                Optional optional = null;
                try {
                    optional = invokeGetter(from.getClass(),field);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Class aClass = field.getType();

//            try {
//                Method[] methods = aClass.getDeclaredMethods();
//                for (Method method : methods) {
//                    if(method.getName().contains("get")){
//                        method.invoke(aClass,null);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return objectMapper.convertValue(from,to);
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
