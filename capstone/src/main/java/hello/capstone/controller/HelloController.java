package hello.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
public class HelloController {
	
    @GetMapping("/sign_up")
    public String sign_up(@RequestParam (required=false) Long id, @RequestParam (required=false) String pw ){
    	log.info("id ={}",id);
    	log.info("pw={}",pw);
        return "asd";
    }
    
    
    
}























