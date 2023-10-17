package hello.capstone.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
	
	private int memberIdx;
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String phone;
	private String social;
	private String role;
	private Date redate;
	private String trust;
	
	public Member() {
		
	}

	
	
}
