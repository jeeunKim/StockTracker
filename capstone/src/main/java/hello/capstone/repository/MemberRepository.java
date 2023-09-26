package hello.capstone.repository;


import java.util.List;

import org.springframework.stereotype.Repository;

import hello.capstone.dto.Member;
import hello.capstone.dto.Shop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

   private final LoginMapper loginMapper;
   private final MemberMapper memberMapper;
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
   public Member saveSocial(Member member) {
      
      loginMapper.saveSocial(member);
      Member findMember = findById(member.getId(), member.getSocial());
      return findMember;
   }
   
   
   /*
    * 아이디로 회원 검색 
    * */
   public Member findById(String id, String social) {
	   Member findMember = loginMapper.findbyid(id, social);
	   
	   return findMember;
   }
   
 
   /*
    * 회원 인덱스
    */
   public int getMeberIdx(Member member) {
	   int idx = loginMapper.getMeberIdx(member);
  
	   return idx;
   }
   
   /*
    * 즐겨찾기한 가게찾기
    */
   public List<Shop> getMyBookmarkedShop(int memberidx) {
	   return memberMapper.getMyBookmarkedShop(memberidx); 
   }
   
   /*
    * 즐겨찾기 등록
    */
   public void bookmarkRegistraion(int memberIdx, int shopIdx) {
	   
	   memberMapper.bookmarkRegistraion(memberIdx, shopIdx);
   }
  
   /*
    * 닉네임 변경
    */
   public void updateNickname(Member member, String nickname) {
	   log.info("member, nickname = {} {} ", member, nickname);
	   memberMapper.updateNickname(member, nickname);
   }
   
   
   
   
}