package hello.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.dto.Member;
import hello.capstone.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	/*
	 * 마지막수정 09/15 16시 41분
	 * */
    @GetMapping("/sign_up")
    public String sign_up(@ModelAttribute("member") Member member ){
    	
    	log.info("id ={}",member.getId());
    	log.info("pw={}",member.getPw());
    	log.info("name ={}",member.getName());
    	log.info("phone={}",member.getPhone());
    	log.info("email ={}",member.getEmail());
    	
    	String success = loginService.signUp(member);
    	
        return success;
    }
    
    
    
}























