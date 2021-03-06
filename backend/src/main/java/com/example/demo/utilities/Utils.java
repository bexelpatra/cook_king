package com.example.demo.utilities;

import com.example.demo.chain.Wallet;
import com.example.demo.dto.ContentDto;
import com.example.demo.dto.MultiFileDto;
import com.example.demo.entity.UsersEntity;
import com.example.demo.entity.WalletEntity;
import com.example.demo.enums.ContentKind;
import com.example.demo.testing.MyEnum;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class Utils<T, E> {
    static ObjectMapper objectMapper = new ObjectMapper();
    static Random random=new Random();
    private static final int IMG_WIDTH = 300;
    private static final int IMG_HEIGHT = 200;
    private static final String localPath ="frontend/public/imgs/";
    private static final String urlPath ="imgs/";

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

    // reflection testing
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
    // todo ================================================================================================
    // todo converter
    // todo ================================================================================================

    // basic converter
    public static<F,T> T to(Class<T> to, F from){
        if(from == null) return null;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        return objectMapper.convertValue(from,to);
    }
    // type reference converter : only possible dto object or entity object;
    public static<F,T> T to(F from){
        if(from == null) return null;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        Class to = null;
        Class to2 = null;
        String className = from.getClass().getName();
        className =className.substring(className.lastIndexOf(".")+1).toLowerCase();
        try {
            if(className.contains("entity")){
                 to2 = from.getClass().getClassLoader().loadClass(from.getClass().getName().replace("Entity","Dto").replace("entity","dto"));
                to = ClassLoader.getSystemClassLoader().loadClass(from.getClass().getName().replace("Entity","Dto").replace("entity","dto"));
            }else if(className.contains("dto")) {
                 to2 = from.getClass().getClassLoader().loadClass(from.getClass().getName().replace("Dto","Entity").replace("dto","entity"));
                to = ClassLoader.getSystemClassLoader().loadClass(from.getClass().getName().replace("Dto","Entity").replace("dto","entity"));
            }else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
        if(objectMapper.convertValue(from,to2).equals(objectMapper.convertValue(from,to))){
            System.out.println("same");
        }
        return (T)objectMapper.convertValue(from,to2);
    }
    private static<T> Class opponentClass(Class<T> from){
        Class objectClass = null;
        String className = from.getClass().getName();
        className =className.substring(className.lastIndexOf(".")+1).toLowerCase();
        try {
            if(className.contains("entity")){
                objectClass = from.getClass().getClassLoader().loadClass(from.getClass().getName().replace("Entity","Dto").replace("entity","dto"));
            }else if(className.contains("dto")) {
                objectClass = from.getClass().getClassLoader().loadClass(from.getClass().getName().replace("Dto","Entity").replace("dto","entity"));
            }else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
        return objectClass;
    }
    // convert List
    public static<F,T> List<T> to(Class<T> to, List<F> froms){
        List<T> ts = new ArrayList<>();
        if(froms.size() == 0) return ts;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        for (F f : froms) {
            T convertValue =objectMapper.convertValue(f,to);
            if(convertValue!=null){
                ts.add(convertValue);
            }
        }
        return ts;
    }

    // make random int
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

    public static List<ContentDto> multiFileConverter(MultiFileDto multiFileDto){
        int a = 0;
        MultipartFile[] files = multiFileDto.getFile();
        String[] texts = multiFileDto.getText();
        Integer[] orders = multiFileDto.getOrder();
        Integer[] kinds = multiFileDto.getKind();
        List<ContentDto> contentDtos = new ArrayList<>();

        for(int i=0; i<files.length;i++){
            contentDtos.add(ContentDto.builder()
                    .description(texts[i])
                    .order(orders[i] == null ? a++:orders[i] )
                    .contentKind(ContentKind.of(kinds[i]) == null? ContentKind.IMAGE: ContentKind.of(kinds[i]))
                    .build());
        }

        a=0;
        return contentDtos;
    }

    public static void saveImage(MultipartFile multipartFile, UsersEntity UsersEntity, int recipeId, int order) throws Exception{

        String savePath = String.format("%s/%d/%d/",localPath,UsersEntity.getId(),recipeId);
        String fileName = String.format("%d.png",order);
        File file = new File(savePath);
        if(!file.exists())file.mkdirs();
        File saveFile = new File(savePath+fileName);
        // 원본크기 그대로 저장하는 방식
//        FileOutputStream fileOutputStream=new FileOutputStream(String.format("%s%s",savePath,fileName));
//        fileOutputStream.write(multipartFile.getBytes());
//        fileOutputStream.close();
        InputStream inputStream = multipartFile.getInputStream();
        resize(inputStream, Paths.get(saveFile.getAbsolutePath()),IMG_WIDTH,IMG_HEIGHT);
        inputStream.close();
    }
    public static void saveImage( MultipartFile[] multipartFile, UsersEntity UsersEntity, int recipeId) throws Exception{
        int count = 1;
        for (MultipartFile file : multipartFile) {
            saveImage(file,UsersEntity,recipeId,count++);
        }
        count = 0;
    }

    public static void deleteAndSaveImage(MultipartFile multipartFile, UsersEntity UsersEntity, int recipeId, int order) throws Exception{
        String savePath = String.format("%s/%d/%d/",localPath,UsersEntity.getId(),recipeId);
        String fileName = String.format("%d.png",order);
        File file = new File(savePath);
        if(!file.exists()){
            file.mkdirs();
        }else{
            deleteRecursive(new File(savePath));
        }
//        File save = new File(String.format())
        FileOutputStream fileOutputStream=new FileOutputStream(new File(String.format("%s%s",savePath,fileName)));
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
    }

    public static void deleteAndSaveImage(MultipartFile[] multipartFile, UsersEntity UsersEntity, int recipeId) throws Exception{
        int count = 1;
        String savePath = String.format("%s/%d/%d/",localPath,UsersEntity.getId(),recipeId);
        File deleteFile = new File(savePath);
        if(!deleteFile.exists()){
            deleteFile.mkdirs();
        }else{
            deleteRecursive(new File(savePath));
        }

        for (MultipartFile file : multipartFile) {
            saveImage(file,UsersEntity,recipeId,count++);
        }
        count = 0;
    }
    public static void deleteRecursive(File file){
        if(file.isDirectory()){
            File[] deleteFiles = file.listFiles();
            for (File deleteFile : deleteFiles) {
                deleteRecursive(deleteFile);
            }
            file.delete();
        }else if(file.isFile()) {
            file.delete();
        }
    }

    private static void resize(InputStream input, Path target,
                               int width, int height) throws IOException {

        // read an image to BufferedImage for processing
        BufferedImage originalImage = ImageIO.read(input);

        // create a new BufferedImage for drawing
        BufferedImage newResizedImage
                = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newResizedImage.createGraphics();

        //g.setBackground(Color.WHITE);
        //g.setPaint(Color.WHITE);

        // background transparent
        g.setComposite(AlphaComposite.Src);
        g.fillRect(0, 0, width, height);

        /* try addRenderingHints()
        // VALUE_RENDER_DEFAULT = good tradeoff of performance vs quality
        // VALUE_RENDER_SPEED   = prefer speed
        // VALUE_RENDER_QUALITY = prefer quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                              RenderingHints.VALUE_RENDER_QUALITY);

        // controls how image pixels are filtered or resampled
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                              RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // antialiasing, on
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);*/

        Map<RenderingHints.Key,Object> hints = new HashMap<>();
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.addRenderingHints(hints);

        // puts the original image into the newResizedImage
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        // get file extension
        String s = target.getFileName().toString();
        String fileExtension = s.substring(s.lastIndexOf(".") + 1);

        // we want image in png format
        ImageIO.write(newResizedImage, fileExtension, target.toFile());

    }
    public static byte[] toBytes(JsonArray jsonArray){
        byte[] bytes = new byte[jsonArray.size()];
        for(int i=0; i< jsonArray.size();i++) bytes[i] = jsonArray.get(i).getAsByte();
        return bytes;
    }
    public static PublicKey toPublicKey(List<Byte> key){
        byte[] bytes = new byte[key.size()];
        for(int i=0; i< key.size();i++) bytes[i] = key.get(i);
        return keyConverter(bytes,PublicKey.class);
    }
    public static PublicKey toPublicKey(JsonArray key){
        return keyConverter(toBytes(key),PublicKey.class);
    }
    public static PrivateKey toPrivateKey(JsonArray key){
        return keyConverter(toBytes(key),PrivateKey.class);
    }
    public static PrivateKey toPrivateKey(List<Byte> key){
        byte[] bytes = new byte[key.size()];
        for(int i=0; i< key.size();i++)bytes[i] = key.get(i);
        return keyConverter(bytes,PrivateKey.class);
    }
    public static PublicKey toPublicKey(byte[] key){ return keyConverter(key,PublicKey.class); }
    public static PrivateKey toPrivateKey(byte[] key){ return keyConverter(key,PrivateKey.class); }

    private static <T,F extends Key> F keyConverter(byte[] key, Class<F> aclass){
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA","BC");
            if(aclass.getName().equals(PrivateKey.class.getName())){
                return (F) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
            }else if(aclass.getName().equals(PublicKey.class.getName())){
                return (F) keyFactory.generatePublic(new X509EncodedKeySpec(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String hex(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            builder.append(Integer.toHexString(bytes[i]));
        }
        return builder.toString();
    }
    private static JsonParser jsonParser = new JsonParser();

    public static Wallet wallet(WalletEntity walletEntity){
        return new Wallet(toBytes(jsonParser.parse(walletEntity.getPrivateKey()).getAsJsonArray()),toBytes(jsonParser.parse(walletEntity.getPublicKey()).getAsJsonArray()));
    }

    public static String join(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (byte aByte : bytes) {
            builder.append(aByte);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }
}
