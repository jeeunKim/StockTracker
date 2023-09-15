package hello.capstone.service;



import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
		member.setRole("롤");
		member.setNickname("닉네임");
		member.setAddress("주소");
		member.setRedate(redate);
	;
		/*
		 * 객체끼리 비교하려했는데  currentTimeMillis때문에 비교가 무조건 false인듯, id랑 pw만 비교함
		 * */
//	    Member findMember = duplicateCheck(member);
//	    if(Objects.equals(findMember,member)) {
//	    	log.info("success={}", "fail");
//	    	return "fail";
//	    }
	
	    Member findMember = duplicateCheck(member);
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
	
	
	
	
	//----------------------------------------------------------------------------------
}
