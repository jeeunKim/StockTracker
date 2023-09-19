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
    * 회원정보저장 마지막수정 09/18 23시 20분
    */
   public boolean save(Member member) {
      
      loginMapper.save(member);
      return true;
   }
   
   /*
    * 카카오회원정보저장
    */
   public boolean saveKakao(Member member) {
      
      loginMapper.saveKakao(member);
      return true;
   }
   
   
   /*
    * 아이디로 회원 검색 
    * */
   public Member findById(String Id) {
	   Member findMember = loginMapper.findbyid(Id);
	   
	   return findMember;
   }
 
  
   
   
   
   
}