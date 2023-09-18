package hello.capstone.service;



import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import hello.capstone.dto.Member;
import hello.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
	
	private final MemberRepository memberRepository;
	/*
	 * 회원가입 메소드 마지막 수정 09/15/16시 41분
	 * */
	public String signUp(Member member) {
		
		long miliseconds = System.currentTimeMillis();
		Date redate = new Date(miliseconds);
		member.setNickname(createRandomNickname());
		member.setSocial("own");
		member.setRedate(redate);
	
	
	    Member findMember = duplicateCheck(member);
	    if(!(Objects.isNull(findMember) ))
		    if(findMember.getId().equals(member.getId()) && findMember.getPw().equals(member.getPw()) ){
		    	
		    	log.info("duplicateCheck = {}", "fail");
		    	return "fail";
		    }
	
		memberRepository.save(member);
		
		return "success";
	}
	//중복회원 검사
	private Member duplicateCheck(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		
		return findMember;
	}
	//닉네임 임의 random String
	private String createRandomNickname() {
		int randomNicknameLen = 8;
		boolean useLetters = true;
		boolean useNumbers = false;
		String randomNick = RandomStringUtils.random(randomNicknameLen, useLetters, useNumbers);
			
		log.info("create random nickname = {} ", randomNick);
			
		return randomNick;
	}
	
	
	
	//----------------------------------------------------------------------------------
	
	
	public Member login(String id, String pw) {
		Member userMember = memberRepository.findById(id);
		boolean pwCheck = passwordCheck(userMember, pw);
		//여기도 Exception 처리하는게 좋을듯
		
		if(pwCheck == false) {
			log.info("fail");
			return null;
		}
		//false 에러를 어떻게 처리할지 모르갰음
		return userMember;
	}
	
	private boolean passwordCheck(Member userMember, String pw) {
		boolean pwCheck = true;
		if(!(userMember.getPw().equals(pw))) {
			//아니면 여기나
			pwCheck = false;
		}
		
		return pwCheck;
	}
}
