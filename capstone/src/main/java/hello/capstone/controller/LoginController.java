package hello.capstone.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.dto.Member;
import hello.capstone.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	/*
	 * 마지막수정 09/15 16시 41분
	 * */
    @PostMapping("/join")
    public String signUp(@RequestBody Member member){
    	
    	log.info("id ={}",member.getId());
    	log.info("pw={}",member.getPw());
    	log.info("name ={}",member.getName());
    	log.info("phone={}",member.getPhone());
    	log.info("role={}",member.getRole());
    	
    	boolean success = loginService.signUp(member);
    	
    	
    	log.info("SignUp Success !");
		return "/login";
    }
    
    @PostMapping("/login_attempt")
    public String login(@RequestBody HashMap<String, Object> loginMap, HttpServletRequest request) {
    	String id = (String) loginMap.get("id");
    	String pw = (String) loginMap.get("pw");
		
    	if(id.isEmpty()) {
    		return "/login";
    	}
    	Member userMember = loginService.login(id, pw);
    	HttpSession session = request.getSession();
    	
    	
    	
    	session.setAttribute("id", userMember.getId());
    	session.setAttribute("name", userMember.getName());
    	session.setAttribute("nickname", userMember.getNickname());
    	
    	log.info("loginId={}",userMember.getId());
    	log.info("loginName={}",userMember.getName());
    	
    	return "home_user";
    }
    
    
    
}























