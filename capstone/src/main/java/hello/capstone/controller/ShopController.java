package hello.capstone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hello.capstone.dto.Member;
import hello.capstone.dto.Ratings;
import hello.capstone.dto.Shop;
import hello.capstone.exception.LogInException;
import hello.capstone.exception.ValidationException;
import hello.capstone.exception.errorcode.ErrorCode;
import hello.capstone.service.MemberService;
import hello.capstone.service.ShopService;
import hello.capstone.validation.group.SaveShopValidationGroup;
import hello.capstone.validation.group.UpdateShopValidationGroup;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopController {
	
	private final MemberService memberService;
	private final ShopService shopService;
	
	
//	@PostMapping("/shopRegistration")
//	public void shopRegistration(@RequestParam(value = "imageFilename", required = false) MultipartFile Image,
//								   @RequestParam(value = "shopidx", defaultValue = "0") String sidx,
//			  					   @RequestParam("shopName") String shopName,
//			  					   @RequestParam("shopTel") String shopTel,
//			  					   @RequestParam(value = "shopAddress", required = false) String shopAddress,
//			  					   @RequestParam("promotionText") String promotionText,
//			  					   @RequestParam("shopWebsite") String shopWebsite,
//			  					   @RequestParam(value = "existingAddress", required = false) String existingAddress,
//			  					   @RequestParam(value = "existingImage", required = false) String existingImage,
//			  					   @RequestParam(value = "method", defaultValue = "register") String method,
//			  					   HttpSession session) throws IllegalStateException, IOException {
//		
//		
//		Member ownerMember = (Member)session.getAttribute("member");
//		int memberidx = memberService.getMeberIdx(ownerMember);
//		
//		Shop shop = new Shop();
//		shop.setShopName(shopName);
//		shop.setShopTel(shopTel);
//		shop.setPromotionText(promotionText);
//		shop.setShopWebsite(shopWebsite);
//		shop.setOwnerIdx(memberidx);
//		
//		if(Image != null) {
//			String fullPath = fileDir + Image.getOriginalFilename();
//			log.info("파일 저장 fullPath ={}",fullPath);
//			int shopidx = Integer.parseInt(sidx);
//			shop.setShopidx(shopidx);
//			Image.transferTo(new File(fullPath));
//			shop.setImageFilename(Image.getOriginalFilename());
//		}
//		else {
//			int shopidx = Integer.parseInt(sidx);
//			shop.setShopidx(shopidx);
//			shop.setImageFilename(existingImage);
//		}
//		
//		if(shopAddress.equals("")) {
//			log.info("existingAddress = {}", existingAddress);
//			shop.setShopAddress(existingAddress);
//		}
//		else {
//			shop.setShopAddress(shopAddress);
//		}
//		
//		shopService.saveShop(shop, method);
//		
//		
//	}
	/*
	 * 가게 등록
	 */
	@PostMapping("/shop/create")
	public void shopCreate(@Validated(value = SaveShopValidationGroup.class) @ModelAttribute Shop shop, 
							BindingResult bindingResult, HttpSession session) 
									throws IllegalStateException, IOException {
		if(bindingResult.hasErrors()) {
    		Map<String, String> errors = new HashMap<>();
	    	for (FieldError error : bindingResult.getFieldErrors()) {
	            log.info("error in {} = {}", error.getField(), error.getDefaultMessage());
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	    	throw new ValidationException(errors);
    	}
		
		Member member = (Member) session.getAttribute("member");
		shop.setOwnerIdx(member.getMemberIdx());
		log.info("shop = {}", shop);
		shopService.saveShop(shop);
	}
	
	
	/*
	 * 가게 수정 - 이미지를 변경하지 않을떄 multipartFile가 null이기 때문에 @RequestParam으로 따로따로 받음
	 */
	@PutMapping("/shop/update")
	public void shopUpdate(@Validated(value = UpdateShopValidationGroup.class) @ModelAttribute Shop shop, BindingResult bindingResult,
						   @RequestParam(value = "imagefile", required = false) MultipartFile Image ) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
    		Map<String, String> errors = new HashMap<>();
	    	for (FieldError error : bindingResult.getFieldErrors()) {
	            log.info("error in {} = {}", error.getField(), error.getDefaultMessage());
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	    	throw new ValidationException(errors);
    	}
		
		shopService.updateShop(shop, Image, shop.getShopAddress());
	}
	
	/*
	 * 가게 삭제
	 */
	@DeleteMapping("/shop/delete")
	public void shopDelete(@RequestParam("shopidx") int shopIdx) {
		shopService.deleteShop(shopIdx);
	}
	
	
	/*
	 * 본인 인증(pw 확인)
	 */
	@PostMapping("/Pw_verification")
	public String verification(@RequestParam String check_pw, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		String real_pw = member.getPw();
		
		if(real_pw.equals(check_pw)) {
			return "";
		}
		else {
			throw new LogInException(ErrorCode.PASSWORD_MISMATCH, null);
		}
	}
	
	
	/*
	 * 지도 shop marker 표시 (상품이 등록되어있는 모든 shop)
	 */
	@GetMapping("/ShopMarker")
	public List<Shop> ShopAddress(HttpSession session){
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
	 * 필터를 적용한 가게 조회 거리, 가격, 마감시간
	 */
	@GetMapping("/getShop/filter")
    public List<Shop> getShopFilterDistance(@RequestParam("latitude") String myLatitude,
		                                    @RequestParam("longitude") String myLongitude,
		                                    @RequestParam(value = "distance", defaultValue = "0") String distance,
		                                    @RequestParam(value = "unit", defaultValue = "km") String unit,
		                                    @RequestParam(value = "price", defaultValue = "0") String itemprice,
		                                    @RequestParam(value = "time", defaultValue = "0") String time,
		                                    @RequestParam(value = "rating", defaultValue = "0") String shoprating){
      

       List<Shop> allShops = shopService.getShops();
      
       double latitude = Double.parseDouble(myLatitude);
       double longitude = Double.parseDouble(myLongitude);
       double dist = Double.parseDouble(distance);
       int price = Integer.parseInt(itemprice);
       double rating = Double.parseDouble(shoprating);
       long minute = Long.parseLong(time) * 60;
       
       
       if(dist != 0) {
          List<Shop> distanceFilteredShops = shopService.runDistanceFilter(latitude, longitude, dist, unit);
          if(distanceFilteredShops != null) {   
             allShops.retainAll(distanceFilteredShops); 
          }
       }   
       if(price != 0) {
          List<Shop> priceFilteredShops = shopService.runPriceFilter(price);
          if(priceFilteredShops != null) {   
             allShops.retainAll(priceFilteredShops);
          }
       }
       if(minute != 0) {
           List<Shop> deadlineFilteredShops = shopService.runDeadlineFilter(minute);   
           if(deadlineFilteredShops != null) {   
              allShops.retainAll(deadlineFilteredShops); 
           }
        }       
       if(rating != 0) {
    	   List<Shop> ratingFilteredShops = shopService.runRatingFilter(rating);
    	   if(ratingFilteredShops != null) {
    		   allShops.retainAll(ratingFilteredShops);
    	   }
       }
      
       return allShops;
    }
    
  
    /*
     * 별점 추가
     */
    @PostMapping("/setRating")
    public void setRating(HttpSession session,
                          @RequestParam("shopidx") int shopidx,
                          @RequestParam("rating") int rating) {
       
       
       Member member = (Member) session.getAttribute("member");
       int memberidx = member.getMemberIdx(); 
       
       Ratings ratings = new Ratings(0,shopidx,memberidx,rating);
       
       shopService.setRating(ratings);
    }  

    /*
     * 해당 아이템 별로 예약자 리스트 조회
     */
    @GetMapping("/shop/item/reservations")
    public List<Map<String, Object>> getItemReservations(@RequestParam("itemidx") int itemidx){
       log.info("itemidx = {}", itemidx);
       
       List<Map<String, Object>> map = shopService.getItemReservations(itemidx);
       
       log.info("map.reservationidx={}", map.get(0).get("reservationidx"));
       
       return shopService.getItemReservations(itemidx);
    }
    
	
	

}