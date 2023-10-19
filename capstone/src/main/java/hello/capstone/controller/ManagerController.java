package hello.capstone.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.dto.AlarmWithBefore;
import hello.capstone.dto.Item;
import hello.capstone.dto.Member;
import hello.capstone.dto.Notice;
import hello.capstone.dto.Reservation;
import hello.capstone.dto.Shop;
import hello.capstone.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
 * 관리자 모드, 회원관리, 가게관리, 상품관리, 공지사항 등등
 */

@Slf4j
@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
	
	private final ManagerService managerService;
	
	//공지사항------------------------------------------------------------------------------------------
	
	/*
	 * 공지사항 CREATE
	 */
	@PostMapping("/notice/create")
	public void noticeCreate(@RequestBody Notice notice) {
		managerService.noticeCreate(notice);
		
	}
	
	/*
	 * 공지사항 READ 
	 */
	@GetMapping("/notice/read")
	public Notice noticeRead(@RequestParam String noticeIdx, @RequestParam String title ){
		int idx = Integer.parseInt(noticeIdx);
		
		return managerService.noticeRead(idx, title);
	}
	
	/*
	 * 공지사항 UPDATE
	 */
	@PutMapping("/notice/update")
	public void noticeUpdate(@RequestBody Notice newNotice) {
		managerService.noticeUpdate(newNotice);
	}
	
	/*
	 * 공지사항 DELETE
	 */
	@DeleteMapping("/notice/delete")
	public void noticeDelete(@RequestBody Notice notice) {
		managerService.noticeDelete(notice);
	}
	
	/*
	 * 모든 공지사항 READ 
	 */
	@GetMapping("/notice/readall")
	public List<Notice> noticeReadAll(){
		
		return managerService.noticeReadAll();
	}
	
	/*
	 * 공지사항 알림 가져오기
	 */
	@GetMapping("/notice/getalarm")
	public List<AlarmWithBefore> getNoticeAlarm(){
		List<AlarmWithBefore> alarmNotices = new ArrayList<AlarmWithBefore>();
		List<Notice> notices = managerService.noticeReadAll();
		//24시간 이전에 올라온 공지사항만 가져오기
		for(Notice notice : notices) {
			
			LocalDateTime dateTime = LocalDateTime.now();
			Timestamp timestamp = Timestamp.valueOf(dateTime);
			//MySql의 타임존을 반영하여 9시간을 더해줌.
			Timestamp now = new Timestamp(timestamp.getTime() + (9 * 60 * 60 * 1000));
			//시간단위로 차이 구하기
	        int before = (int) ((now.getTime() - notice.getNoticeDate().getTime()) / (1000 * 60 * 60));
	        
	        
	        // 만약 24시간 이내라면 알림 리스트에 추가
	        if (before <= 24) {
	            AlarmWithBefore alarm = new AlarmWithBefore(notice, before);
	            alarmNotices.add(alarm);
	        }
		}
		
		
		return alarmNotices;
	}
	
	
	// 사용자 관리------------------------------------------------------------------------------------------
	
	/*
	 * 일반 사용자 조회
	 */
	@GetMapping("/member/user")
	public List<Member> getUserMember(){
		
		return managerService.getMemberByRole("사용자");
	}
	
	/*
	 * 신뢰도를 깎은 가게와 예약 날짜 조회
	 */
	@GetMapping("/member/user/trustmanage")
	public List<Map<String, Object>>  trustManage(@RequestParam("memberIdx") String memberidx){
		int memberIdx = Integer.parseInt(memberidx);
		
		
		return managerService.getFailedReservation(memberIdx);
	}
	
	/*
	 * 신뢰도를 깎은 가게에서 어떤 아이템을 예약했었는지 조회
	 */
	@GetMapping("/member/user/trustmanage/item")
	public List<Item> failItem(@RequestParam("shopIdx") String shopidx){
		int shopIdx = Integer.parseInt(shopidx);
		
		
		return managerService.getFailedItems(shopIdx);
	}
	

	//상업자 관리---------------------------------------------------------------------------------------------
	
	/*
	 * 상업자 맴버 조회 
	 */
	@GetMapping("/member/business")
	public List<Member> getBusinessMember() {
		
		return managerService.getMemberByRole("상업자");
	}
	
	/*
	 * 해당 상업자의 가게 정보 조회
	 */
	@GetMapping("/member/business/shopinfo")
	public List<Shop> getShopinfoByBusiness(@RequestParam("memberidx") int owneridx){
		
		return managerService.getShopinfoByBusiness(owneridx);
	}
	
	/*
	 * 해당 가게에 등록했던 상품 조회
	 */
	@GetMapping("/member/business/iteminfo")
	public List<Item> getIteminfoByBusniess(@RequestParam("shopidx") int shopidx){
		return managerService.getIteminfoByBusiness(shopidx);
	}
	
	
	
	//관리자 관리---------------------------------------------------------------------------------------
	
	/*
	 *  관리자 맴버 조회 
	 */
	@GetMapping("/member/admin")
	public List<Member> getAdminMember(){
		
		return managerService.getMemberByRole("관리자");
	}
		
	
	
	//가게 분석---------------------------------------------------------------------------------------
	
	/*
	 * 모든 가게 정보 조회
	 */
	@GetMapping("/analysis/shop")
	public List<Shop> getShopinfo(){
		return managerService.getShopinfo();
	}
	
	/*
	 * 해당 가게에 등록된 상품과 상품별 예약자 수 조회
	 */
	@GetMapping("/analysis/item")
	public List<Map<String, Object>> AnalysisItem(@RequestParam("shopidx") int shopidx){
		return managerService.getIteminfo(shopidx);
	}
	
	/*
	 * 해당 가게에서 상품을 구매해간 고객 정보 
	 */
	@GetMapping("/analysis/reservation/member")
	public List<Member> AnalysisReservation(@RequestParam("shopidx") int shopidx){
		return managerService.getReservationMember(shopidx);
	}
	
	
	//검색---------------------------------------------------------------------------------------------------
	
	/*
	 * 모든 아이템 나열
	 */
	@GetMapping("/item/all")
	public List<Map<String, Object>> getItemAll(){
		
		
		return managerService.getItemAll();
	}
	
	
	/*
	 * 이름으로 회원검색 - 이름순, 날짜순
	 */
	@GetMapping("/search/member")
	public List<Member> searchMemberByName(@RequestParam("name") String name){
		
		return managerService.searchMemberByName(name);
	}
	
	
	/*
	 * 이름으로 가게검색 - 이름순, 날짜순 주인이름 포함
	 */
	@GetMapping("/search/shop")
	public List<Map<String, Object>> searchShopByName(@RequestParam("shopname") String shopName){
		
		return managerService.searchShopByName(shopName);
	}
	
	
	/*
	 * 이름으로 아이템검색 - 이름순, 날짜순 가게이름 포함
	 */
	@GetMapping("/search/item")
	public List<Map<String, Object>> searchItemByName(@RequestParam("itemname") String itemName){
		
		return managerService.searchItemByName(itemName);
	}
	
	
	//통계------------------------------------------------------------------------------------------------
	
	/*
	 * 소셜(kakao, naver), 일반 회원의 수 통계
	 */
	@GetMapping("/member/count")
	public HashMap<String, Integer> memberCount(){
		HashMap<String, Integer> numberOfMembersBySocial = new HashMap<String, Integer>();
		
		int kakao = managerService.getMemeberCountBySocial("kakao");
		numberOfMembersBySocial.put("kakao", kakao);
		
		int naver = managerService.getMemeberCountBySocial("naver");
		numberOfMembersBySocial.put("naver", naver);
		
		int normal = managerService.getMemeberCountBySocial("normal");
		numberOfMembersBySocial.put("normal", normal);
		
		return numberOfMembersBySocial;
				
	}
	
	/*
	 * 각 별점대별 shop 조회
	 */
	@GetMapping("/shop/count")
	public HashMap<Integer, List<Shop>> ShopCount(){
		HashMap<Integer, List<Shop>> numberOfShopsByRating = new HashMap<Integer, List<Shop>>();
		
		numberOfShopsByRating.put(0, managerService.getShopByRating(0));
		
		numberOfShopsByRating.put(1, managerService.getShopByRating(1));
		
		numberOfShopsByRating.put(2, managerService.getShopByRating(2));
		
		numberOfShopsByRating.put(3, managerService.getShopByRating(3));
		
		numberOfShopsByRating.put(4, managerService.getShopByRating(4));
		
		return numberOfShopsByRating;		
	}
	
	
	
}

















