package hello.capstone.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hello.capstone.dto.Member;
import hello.capstone.dto.Shop;
import hello.capstone.service.MemberService;
import hello.capstone.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopController {
	
	private final MemberService memberService;
	private final ShopService shopService;
	
	@Value("${file.dir}")
	private String fileDir;
	
	@PostMapping("/shopRegistration")
	public String shopRegistration(@RequestParam("imageFilename") MultipartFile Image,
			  					   @RequestParam("shopName") String shopName,
			  					   @RequestParam("shopTel") String shopTel,
			  					   @RequestParam("shopAddress") String shopAddress,
			  					   @RequestParam("promotionText") String promotionText,
			  					   @RequestParam("shopWebsite") String shopWebsite,
			  					   HttpSession session) throws IllegalStateException, IOException {
		
		Member ownerMember = (Member)session.getAttribute("member");
		int memberidx = memberService.getMeberIdx(ownerMember);
		
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setShopTel(shopTel);
		shop.setPromotionText(promotionText);
		shop.setShopAddress(shopAddress);
		shop.setShopWebsite(shopWebsite);
		shop.setOwnerIdx(memberidx);
		
		if(!Image.isEmpty()) {
			String fullPath = fileDir + Image.getOriginalFilename();
			log.info("파일 저장 fullPath ={}",fullPath);
			Image.transferTo(new File(fullPath));
		}
		shop.setImageFilename(Image.getOriginalFilename());
		log.info("가게 파라미터={}",shop);
		log.info("파일 파라미터={}",Image);
		shopService.saveShop(shop);
		
		
		return "/home_user";
	}
	
	/*
	 * 지도 shop marker 표시 테스트용 (모든 shop)
	 */
	@GetMapping("/ShopMarker")
	public List<Shop> ShopAddress(){
		List<Shop> shops = shopService.getShops();
		return shops;
	} 
	
	/*
	 * 본인의 가게 정보 가져오기 (상업자 버전)
	 */
	@GetMapping("/getMyShop")
	public List<Shop> getMyShop(HttpSession session){
		Member member = (Member) session.getAttribute("member");
		
		List<Shop> shops = shopService.getShopByMember(memberService.getMeberIdx(member));
		
		return shops;
	}
	
	/*
	 * 거리 필터를 적용한 가게 조회
	 */
	@GetMapping("/getShop/filter/distance")
	public List<Shop> getShopFilterDistance(@RequestParam("latitude") String myLatitude,
											@RequestParam("longitude") String myLongitude,
											@RequestParam("distance") String distance,
											@RequestParam("unit") String unit){
		
		
		double latitude = Double.parseDouble(myLatitude);
		double longitude = Double.parseDouble(myLongitude);
		
		double dist = Double.parseDouble(distance);
		
		List<Shop> distanceFilteredShops = shopService.runDistanceFilter(latitude, longitude, dist, unit);
		
		log.info("distanceFilteredShops = {}", distanceFilteredShops);
		
		return distanceFilteredShops;
	}
	

}