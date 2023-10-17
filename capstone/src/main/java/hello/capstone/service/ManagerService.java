package hello.capstone.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import hello.capstone.dto.Notice;
import hello.capstone.dto.Shop;
import hello.capstone.exception.AlreadyBookmarkedShopException;
import hello.capstone.exception.NullContentException;
import hello.capstone.exception.NullTitleException;
import hello.capstone.exception.errorcode.ErrorCode;
import hello.capstone.repository.ItemRepository;
import hello.capstone.repository.ManagerRepository;
import hello.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final ManagerRepository managerRepository;

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
	
	//----------------------------------------------------------------------------
	
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

















