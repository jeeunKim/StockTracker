package hello.capstone.service;

import java.sql.Date;

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
	
	public int signUp(Member member) {
		long miliseconds = System.currentTimeMillis();
	    Date redate = new Date(miliseconds);
	    member.setRedate(redate);
		memberRepository.save(member);
		
		return 1;
	}
	
}
