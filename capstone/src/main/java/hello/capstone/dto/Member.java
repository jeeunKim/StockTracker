package hello.capstone.dto;

import java.sql.Date;

import hello.capstone.validation.group.SignUpValidationGroup;
import hello.capstone.validation.group.UpdatePwValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 마지막수정 09/18 14시 20분
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
	
	private int memberIdx;
	
	@Email(groups = SignUpValidationGroup.class, message ="이메일 형식의 아이디를 입력하세요.")
    @NotBlank(message = "아이디는 필수 입력항목입니다.(공백 사용X)", groups = SignUpValidationGroup.class)
	private String id;
    
    @Size(min = 8, max = 16, message="비밀번호는 8~16자로 입력하세요.", groups = {SignUpValidationGroup.class,UpdatePwValidationGroup.class})
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,15}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", groups = SignUpValidationGroup.class)
    @NotBlank(message = "비밀번호는 필수 입력항목입니다.(공백 사용X)", groups = SignUpValidationGroup.class)
	private String pw;
    
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,6}$", message = "이름은 2~7자의 한글 또는 영문 대 소문자로 작성해주세요", groups = SignUpValidationGroup.class)
    @NotBlank(message = "이름은 필수 입력항목입니다.(공백 사용X)", groups = SignUpValidationGroup.class)
	private String name;
    
	private String nickname;
    
    @Pattern(regexp = "^01[016789]\\d{7,8}$", message = "핸드폰 번호는 '-'없이 숫자로만 작성해주세요(11)", groups = SignUpValidationGroup.class)
    @NotBlank(message = "핸드폰 번호는 필수 입력항목입니다.(공백 사용X)", groups = SignUpValidationGroup.class)
	private String phone;
     
	private String social;
    
    @NotBlank(message = "담장직은 필수 입력항목입니다.(공백 사용X)", groups = SignUpValidationGroup.class)
	private String role;
    
	private Date redate;
	
	private String trust;

}