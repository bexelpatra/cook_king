package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.CuisineEntity;
import com.example.demo.entity.FirstCategoryEntity;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.repository.CuisineRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;
import com.example.demo.service.TestService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.AES;
import com.example.demo.utilities.SMTP;
import com.example.demo.utilities.Utils;
import javafx.scene.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "test")
public class TestController {

    // todo 의존주입 3가지 방법(repository는 controller에서 사용을 지양한다.)
    // 1.기존의 방식, 권장하지 않는다.
//    @Autowired
//    private UsersRepository usersRepository;

    // 2.새로운 방식, 생성자를 통한 의존주입
//    private final UsersRepository usersRepository;
//    public TestController(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }

    // 3.lombok을 통한 방식
//    private final UsersRepository usersRepository;

    private final TestService testService;
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final CuisineRepository cuisineRepository;
    private final RecipeService recipeService;

//    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    @GetMapping(value = "/test1",produces = MediaType.APPLICATION_JSON_VALUE)  // get 방식은 param만 받는다.
    public ResponseEntity test1(@RequestParam("name")String name, @RequestParam(value = "number",required = false)int x){
        Map<String,Object> result = new HashMap<>();
        result.put("time",System.nanoTime()/1000);
        result.put("name",name);
        result.put("nunmber",x);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = {"/test2","/test2/"},produces = MediaType.APPLICATION_JSON_VALUE) // get을 제외한 방식은 @RequestBody를 통해서 받는다.
    public ResponseEntity test2(@RequestBody UsersDto usersDto){
        Map<String,Object> result = new HashMap<>();
        result.put("user",usersDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/test123",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test123(@RequestParam("nong")String nong, @RequestParam(value = "number",required = false)int x){
        Map<String,Object> result = new HashMap<>();
        result.put("nong",nong);
        result.put("number",x);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    // calendar에 숫자 더하면 instance가 변해버리는가...
    @PostMapping(value = "/test3",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enumtest(@RequestParam Map<String, String> map){
        Map<String,Object> result = new HashMap<>();
        for(int i=0;i<3;i++){
            System.out.println(Utils.dateToStr(Calendar.MINUTE,20));
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = "/test4",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity objectmapperTest(@RequestParam Map<String, String> map){
        Map<String,Object> result = new HashMap<>();
        RecipesEntity recipesEntity = new RecipesEntity();
        FirstCategoryEntity firstCategoryEntity = new FirstCategoryEntity();
        firstCategoryEntity.setName("얍얍얍");
        recipesEntity.setFirstCategoryEntity(firstCategoryEntity);
        Utils.test(RecipesDto.class,new RecipesEntity());
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @PostMapping(value = "/test5",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity classLoader(@RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        RecipesEntity recipesEntity = new RecipesEntity();
        Class aClass = new RecipesDto().getClass();
        Class aClass1 = null;
        Class aClass2 = null;
        Class aClass3 = null;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            aClass = RecipesEntity.class;
            aClass2 = classLoader.loadClass(RecipesEntity.class.getName());
            aClass3 = classLoader.loadClass(RecipesEntity.class.getName().replace("entity","dto").replace("Entity","Dto"));
            for (Method method : aClass2.getDeclaredMethods()) {
                System.out.println(method.getName());
                System.out.println(method.toGenericString());
                Arrays.stream(method.getParameters()).forEach(parameter -> System.out.println(parameter.getName()));
                if(method.getName().contains("get")){
                    RecipesEntity recipesEntity1 =new RecipesEntity();
                    recipesEntity1.setId(1);
                    for (Field field : recipesEntity1.getClass().getDeclaredFields() ) {
                        field.setAccessible(true);
                        System.out.println(field.get(field));
                    }
                    System.out.println(method.invoke(aClass2.newInstance(),null));

                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("dd",aClass1);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @PostMapping(value = "/test6",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test6(@RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        UsersDto usersDto = new UsersDto();
        usersDto.setEmail("ddd");

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = "/test7",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test7(@RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        UsersDto usersDto = new UsersDto();
        UsersEntity usersEntity = (UsersEntity) Utils.to(usersDto);

        result.put("user",usersEntity);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @GetMapping(value = "/test8/{path}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pathvar(@PathVariable("path") String path){
        Map<String,Object> result = new HashMap<>();

        System.out.println(path);
        result.put("path",path);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/test9/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDB(@RequestParam("email")String email,@RequestParam("type")int type){
        Map<String,Object> result = new HashMap<>();
        switch (type){
            case 0:
                testService.test1(email);
                break;
            case 1:
                testService.test2(email);
                break;
            case 2:
                SMTP.send(email,"야야야야양","rkfkfk");
                break;
            case 3:
                SMTP.send(email,"야야야야양","rkfkfk");
                break;
            case 4:
                SMTP.send(email,"야야야야양","rkfkfk");
                break;
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = "/test10",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sample22(@RequestParam("param") String param, @RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        result.put("param",param);
        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file1",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file1(MultipartFile multipartFile){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file2",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file2(@RequestParam TestDto2 testDto2){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file3",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file3(TestDto2 testDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(5000);
        try {
            Utils.saveImage(testDto.getFile(),usersEntity,3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file4",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file4(Wrapper testDto2s){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file5",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file5(@RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/file6",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity file6(@RequestBody MultipartFile multipartFile){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/test11",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test11(@RequestParam(value = "s")int[] s){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        result.put("s",s);

        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/test12",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test12(@RequestParam(value = "token")String token,
                                 @RequestParam(value = "recipe")int recipe){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        RecipesEntity recipesEntity = recipeRepository.getRecipesEntityById(recipe).orElse(null);
        userService.findUsersEntityByToken(token).ifPresent(usersEntity -> usersEntity.getUsersFavoriteRecipes().add(recipesEntity));

        return new ResponseEntity(result,httpStatus);
    }


    @PostMapping(value = "/test13",produces = MediaType.APPLICATION_JSON_VALUE) // 즐겨찾기 추가하기
    public ResponseEntity test13(@RequestParam(value = "token")String token,
                                 @RequestParam(value = "recipe")int recipe){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        int x = userService.findUsersEntityByToken(token).orElseThrow(RuntimeException::new).getId();
        Integer xx = userService.addFavoriteRecipe(x, recipe);

        result.put("result",xx);
        return new ResponseEntity(result,httpStatus);
    }
    @PostMapping(value = "/test14",produces = MediaType.APPLICATION_JSON_VALUE) // 쿼리테스트
    public ResponseEntity test14(@RequestParam("val")int val){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
//        List<RecipesEntity> recipesEntities = recipeRepository.findPopular20Recipes(FirstCategoryKind.of(val));
//        List<RecipesEntity> recipesEntities2 = recipeRepository.findPopular20Recipes(val);

//        result.put("result1",recipesEntities);
//        result.put("result2",recipesEntities2);
        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/test15",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteTest(@RequestParam("path")String path){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Utils.deleteRecursive(new File(path));
        return new ResponseEntity(result,httpStatus);

    }
    @PostMapping(value = "/test16",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postTestFromFront(@RequestParam("s")String path){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        CuisineEntity cuisineEntity = new CuisineEntity();
        cuisineEntity.setName(path);

        result.put("dd",cuisineRepository.save(cuisineEntity));
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);

    }
    @GetMapping(value = "/test17",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pathTest(@RequestParam("s")String path){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        File file = new File(path);
        String x= file.getAbsolutePath();
        String y= recipeService.test(path);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);

    }

    @GetMapping(value = "/test18",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity AESchanger(@RequestParam("s")String path){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        String x = "";
        try {
            x = new AES().aesEncode(path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        result.put("x",x);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    @PostMapping(value = "/test19",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity image(MultiFileDto multiFileDto) throws Exception {
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(10);
        Utils.saveImage(multiFileDto.getFile(),usersEntity,3);

        result.put("url",String.format("imgs/%d/%d/%d.png",10,3,1));
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);

    }
    @PutMapping(value = "/test20",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity corsTest(@RequestBody TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        result.put("test",testDto);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
    @PutMapping(value = "/test21",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity corsTest2(TestDto testDto){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        result.put("test",testDto);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
    @PutMapping(value = "/test22",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity corsTest3(String s1){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        result.put("test",s1);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
    @PutMapping(value = "/test23",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity corsTest4(HttpRequest httpRequest){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        result.put("test",httpRequest);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }

    @GetMapping(value = "/test24")
    public ResponseEntity getImageFile(@RequestParam("pathA")String path){
        Map<String,Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        File dirs = new File("D:\\class\\cook_king\\frontend\\public\\imgs\\1\\20");
        File file = new File("D:\\class\\cook_king\\frontend\\public\\imgs\\4.png");
        System.out.println(file.canRead());
        FileOutputStream fileOutputStream = null;
        byte[] bytes = new byte[(int)file.length()];
        BufferedImage bufferedImage = null;
        BufferedImage bufferedImage2;
        InputStream inputStream = null;
        byte[] bytes2 = new byte[1024*1024];
        int x= 0;
        try {
            bytes2 = Files.readAllBytes(Paths.get("D:\\class\\cook_king\\frontend\\public\\imgs\\4.png"));
//            bytes = Files.readAllBytes(Paths.get("D:\\class\\cook_king\\frontend\\public\\imgs\\1\\20"));
//            inputStream = new BufferedInputStream(new FileInputStream(file));
//
//            x = inputStream.read();
//            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(fileOutputStream);
//            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
//            int numOfBytes = imageInputStream.read(bytes2);
//            inputStream.read(bytes,0,bytes.length);
//
//            bufferedImage = ImageIO.read(file);
//            bufferedImage2 =  ImageIO.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("dirs",dirs);
        result.put("file",file);

        result.put("bytes",bytes);
        result.put("byte2",bytes2);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity(result,httpStatus);
    }
}
