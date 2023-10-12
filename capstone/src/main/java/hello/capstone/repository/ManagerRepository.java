package hello.capstone.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hello.capstone.dto.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ManagerRepository {

	private final ManagerMapper managerMapper;
	
	/*
	 * 공지사항 CREATE
	 */
	public void noticeCreate(Notice notice) {
		managerMapper.noticeCreate(notice);
	}
	
	/*
	 * 공지사항 READ
	 */
	public Notice noticeRead(int noticeIdx, String title) {
		return managerMapper.noticeRead(noticeIdx, title);
	}
	
	/*
	 * 공지사항 UPDATE
	 */
	public void noticeUpdate(Notice newNotice) {
		managerMapper.noticeUpdate(newNotice);
	}
	
	/*
	 * 공지사항 DELETE
	 */
	public void noticeDelete(Notice notice) {
		managerMapper.noticeDelete(notice);
	}
	
	/*
	 * 공지사항 READ
	 */
	public List<Notice> noticeReadAll(){
		return managerMapper.noticeReadAll();
	}
	
	
	
	
}
