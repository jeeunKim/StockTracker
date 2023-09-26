package hello.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.capstone.dto.Member;
import hello.capstone.dto.Shop;
import hello.capstone.exception.AlreadyBookmarkedShopException;
import hello.capstone.exception.SignUpException;
import hello.capstone.exception.errorcode.ErrorCode;
import hello.capstone.repository.MemberRepository;
import hello.capstone.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final ShopRepository shopRepository;
	
	/*
	 * 멤버의 멤버인덱스 조회
	 */
	public int getMeberIdx(Member member) {
		int idx = memberRepository.getMeberIdx(member);
		return idx;
	}
	
	/*
	 * 즐겨찾기 등록
	 */
	public void bookmarkRegistration(int memberIdx, int shopIdx) {
		
		List<Shop> MyBookmarkedShops = memberRepository.getMyBookmarkedShop(memberIdx);
		for (Shop bookmarkShop : MyBookmarkedShops) {
			int idx = shopRepository.getShopIdx(bookmarkShop);
			if (shopIdx == idx){
				throw new AlreadyBookmarkedShopException(ErrorCode.ALREADY_BOOKMARKED_SHOP,null);
			}
		}
		
		memberRepository.bookmarkRegistraion(memberIdx, shopIdx);
	}
	
	/*
	 * 즐겨찾기한 가게들의 인덱스 조회 
	 */
	public List<Shop> getMyBookmarkedShop(int memberIdx) {
		
		
		return memberRepository.getMyBookmarkedShop(memberIdx);
	}
	
	
	/*
	 * 닉네임 변경
	 */
	@Transactional
	public Member updateNickname(Member member, String nickname) {
		memberRepository.updateNickname(member, nickname);
		member.setNickname(nickname);
		
		return member;
	}
	
}











