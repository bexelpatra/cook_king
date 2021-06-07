package com.example.demo.controller;

import com.example.demo.dto.TestDto;
import com.example.demo.dto.UsersDto;
import com.example.demo.service.TestService;
import com.example.demo.utilities.Querying;
import com.example.demo.utilities.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "test")
@RequiredArgsConstructor
@CrossOrigin
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

//    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    @GetMapping(value = "/test1")  // get 방식은 param만 받는다.
    public ResponseEntity test1(@RequestParam("name")String name, @RequestParam(value = "number",required = false)int x){
        Map<String,Object> result = new HashMap<>();
        result.put("time",System.nanoTime()/1000);
        result.put("name",name);
        result.put("nunmber",x);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = {"/test2","/test2/"}) // get을 제외한 방식은 @RequestBody를 통해서 받는다.
    public ResponseEntity test2(@RequestBody UsersDto usersDto){
        Map<String,Object> result = new HashMap<>();

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/test123")
    public ResponseEntity test123(@RequestParam("nong")String nong, @RequestParam(value = "number",required = false)int x){
        Map<String,Object> result = new HashMap<>();
        result.put("nong",nong);
        result.put("number",x);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = "/test3")
    public ResponseEntity enumtest(@RequestParam Map<String, String> map){
        Map<String,Object> result = new HashMap<>();
        for(int i=0;i<3;i++){
            System.out.println(Utils.dateToStr(Calendar.MINUTE,20));
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
