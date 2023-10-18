package hello.capstone.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import hello.capstone.dto.Item;
import hello.capstone.dto.Member;
import hello.capstone.dto.Notice;
import hello.capstone.dto.Reservation;
import hello.capstone.dto.Shop;
import hello.capstone.exception.NullContentException;
import hello.capstone.exception.NullTitleException;
import hello.capstone.exception.errorcode.ErrorCode;
import hello.capstone.repository.ManagerRepository;
import hello.capstone.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final ManagerRepository managerRepository;
	private final ShopRepository shopRepository;

	/*
	 * 공지사항 CREATE
	 */
	public void noticeCreate(Notice notice) {
		
		if(notice.getTitle() == null || notice.getTitle().isEmpty()) {
			throw new NullTitleException(ErrorCode.NULL_TITLE,null);
		}
		if(notice.getContent() == null || notice.getTitle().isEmpty()) {
			throw new NullContentException(ErrorCode.NULL_CONTENT,null);
		}
		
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(dateTime);
		//MySql의 타임존을 반영하여 9시간을 더해줌.
		Timestamp now = new Timestamp(timestamp.getTime() + (9 * 60 * 60 * 1000));
		notice.setNoticeDate(now);
		
		managerRepository.noticeCreate(notice);
	}
	
	/*
	 * 공지사항 READ
	 */
	public Notice noticeRead(int noticeIdx, String title){
		return managerRepository.noticeRead(noticeIdx, title);
	}
	
	/*
	 * 공지사항 UPDATE
	 */
	public void noticeUpdate(Notice newNotice) {
		if(newNotice.getTitle() == null || newNotice.getTitle().isEmpty()) {
			throw new NullTitleException(ErrorCode.NULL_TITLE,null);
		}
		if(newNotice.getContent() == null || newNotice.getTitle().isEmpty()) {
			throw new NullContentException(ErrorCode.NULL_CONTENT,null);
		}
		
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(dateTime);
		//MySql의 타임존을 반영하여 9시간을 더해줌.
		Timestamp now = new Timestamp(timestamp.getTime() + (9 * 60 * 60 * 1000));
		
		newNotice.setNoticeModify("수정됨");
		newNotice.setNoticeDate(now);

		managerRepository.noticeUpdate(newNotice);
	}
	
	/*
	 * 공지사항 DELETE
	 */
	public void noticeDelete(Notice notice) {
		managerRepository.noticeDelete(notice);
	}
	
	/*
	 * 모든 공지사항 READ
	 */
	public List<Notice> noticeReadAll(){
		return managerRepository.noticeReadAll();
	}
	
	// 사용자 관리----------------------------------------------------------------------------
	
	/*
	 * 역할별  사용자 조회, 가나다 순
	 */
	public List<Member> getMemberByRole(String role){
		
		return managerRepository.getMemberByRole(role);
	}
	
	/*
	 * 실패한 예약 조회(신뢰도가 깍인 예약)
	 */
	public List<Pair<Shop, String>> getFailedReservation(int memberIdx){
		List<Pair<Shop, String>> shopAndDate = new ArrayList<Pair<Shop, String>>();
		List<Reservation> failedReservations = managerRepository.getFailedReservation(memberIdx);
		
		//예약 내역에 있는 Shop가져오기, 예약 날짜 가져오기
		for (Reservation fReservation : failedReservations) {
			Shop shop = shopRepository.getShopByIdx(fReservation.getShopidx());
			
			Date reDate = fReservation.getRedate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 형식 지정
	        String reserveDate = sdf.format(reDate);
	        
	        Pair<Shop, String> pair = Pair.of(shop, reserveDate);
			shopAndDate.add(pair);
		}
		
		return shopAndDate;
		
	}
	
	/*
	 * 신뢰도를 깎은 가게에서 어떤 아이템을 예약했었는지 조회
	 */
	public List<Item> getFailedItems(int shopIdx){
		
		return managerRepository.getFailedItems(shopIdx);
	}
	
	
	//사업자 관리----------------------------------------------------------------------------------
	
	
	
	
	//통계---------------------------------------------------------------------------------------
	/*
	 * 소셜 별 회원 수
	 */
	public int getMemeberCountBySocial(String social) {
		return managerRepository.getMemeberCountBySocial(social);
	}
	
	
	/*
	 * 별점 별 Shop조회
	 */
	public List<Shop> getShopByRating(int rating){
		double dRating = (double)rating;
		return managerRepository.getShopByRating(dRating);
	}
	
}

















