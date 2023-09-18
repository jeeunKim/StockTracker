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
    public String signUp(@RequestBody HashMap<String, Object> signUpMap, HttpServletResponse response){
    	
    	log.info("id ={}",signUpMap.get("id"));
    	log.info("pw={}",signUpMap.get("pw"));
    	log.info("name ={}",signUpMap.get("name"));
    	log.info("phone={}",signUpMap.get("phone"));
    	log.info("role={}",signUpMap.get("role"));
    	
    	Member member = new Member();
    	member.setId((String)signUpMap.get("id"));
    	member.setPw((String)signUpMap.get("pw"));
    	member.setName((String)signUpMap.get("name"));
    	member.setPhone((String)signUpMap.get("phone"));
    	member.setRole((String)signUpMap.get("role"));
    	
    	
    	
    	if(loginService.signUp(member).equals("success")) {
    		log.info("SignUp Success");
    		return "redirect:/home_user";
    	    //301은 영구적, 302는 일시적
    	}
    	response.setHeader("Location", "localhost:3000/sign_up;charset=UTF-8");
	    response.setStatus(302);
	    return "sign_up";
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
    	
    	if(userMember == null) {
    		log.info("login = {}", "fail");
			return "fail";
		}
    	
    	session.setAttribute("id", userMember.getId());
    	session.setAttribute("name", userMember.getName());
    	session.setAttribute("nickname", userMember.getNickname());
    	
    	log.info("loginId={}",userMember.getId());
    	log.info("loginName={}",userMember.getName());
    	
    	return "/home_user";
    }
    
    
    
}























