package hello.capstone.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hello.capstone.dto.Notice;
import hello.capstone.dto.Shop;

@Mapper
public interface ManagerMapper {
	
	//공지사항 등록
	void noticeCreate(Notice notice);
	
	//공지사항 읽기
	Notice noticeRead(@Param("noticeIdx") int noticeIdx, @Param("title") String title);
	
	//공지사항 수정
	void noticeUpdate(Notice newNotice);
	
	//공지사항 삭제
	void noticeDelete(Notice notice);

	//모든공지사항 읽기
	List<Notice> noticeReadAll();
	
	//카카오 회원 수 조회
	int getMemeberCountBySocial(String social);

	List<Shop> getShopByRating(double rating);
}
