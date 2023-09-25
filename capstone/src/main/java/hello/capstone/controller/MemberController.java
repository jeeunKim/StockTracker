package hello.capstone.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.dto.Member;
import hello.capstone.dto.Shop;
import hello.capstone.service.MemberService;
import hello.capstone.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final ShopService shopService;
	
	
	/*
	 * 즐겨찾기 등록
	 */
	@PostMapping("/bookmark/registration")
	public String bookmarkRegistration(@RequestBody Member member, @RequestBody Shop shop) {
		
		log.info("member = {} ", member);
		
		int memberIdx = memberService.getMeberIdx(member);
		int shopIdx = shopService.getShopIdx(shop);
		
		memberService.bookmarkRegistration(memberIdx, shopIdx);
		

		return "/home_user";
	}
	
	/*
	 * 즐겨찾기 조회
	 */
	@GetMapping("/bookmark/check")
	public String bookmarkCheck(@ModelAttribute Member member, HttpSession session) {
		log.info("member = {} ", member);
		
		int memberIdx = memberService.getMeberIdx(member);
		
		List<Shop> MyBookmarkedShops = memberService.getMyBookmarkedShop(memberIdx);
		log.info("MyBookmarkedShops = {} ", MyBookmarkedShops);
		
		session.setAttribute("MyBookmarkedShops", MyBookmarkedShops);

		
		return "/bookmark_list";
	}
}
