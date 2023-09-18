package hello.capstone.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
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

	public Member(String id, String pw, String name, String nickname, String phone, String social, String role,
			Date redate, String trust) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.nickname = nickname;
		this.phone = phone;
		this.social = social;
		this.role = role;
		this.redate = redate;
		this.trust = trust;
	}
	
}
