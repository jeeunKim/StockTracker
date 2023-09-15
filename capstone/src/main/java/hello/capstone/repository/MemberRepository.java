package hello.capstone.repository;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import hello.capstone.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

   public final LoginMapper loginMapper;
   /*
    * 회원정보저장 마지막수정 09/15 16시 41분
    */
   public Member save(Member member) {
      
      loginMapper.save(member);
      return member;
   }
   /*
    * 아이디로 회원 검색 마지막수정 09/15 16시 41분
    * */
   public Member findById(String Id) {
	   Member findMember = loginMapper.findbyid(Id);
	   
	   return findMember;
   }
   
   
   
   
   
}