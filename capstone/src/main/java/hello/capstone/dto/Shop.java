package hello.capstone.dto;



import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import hello.capstone.validation.group.NotBlanckGroup;
import hello.capstone.validation.group.PatternCheckGroup;
import hello.capstone.validation.group.SizeCheckGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Shop {
	private int shopidx;
	
	@Size(min = 2, max = 20, message ="가게명은 1 ~ 20자로 입력해주세요.", groups = SizeCheckGroup.class)	
	@NotBlank(message = "가게명은 필수 항목입니다.", groups = NotBlanckGroup.class)
	private String shopName;
	
	private int ownerIdx;
	
	@NotNull(message = "가게 이미지는 필수 항목입니다.", groups = NotBlanckGroup.class)
	private MultipartFile imageFile;
	
	private String imageFilename;
	
	@NotBlank(message = "가게 주소는 필수 항목입니다.", groups = NotBlanckGroup.class)
	private String shopAddress;
	
	@Pattern(regexp = "^\\d{10,11}$", message = "가게 번호는 숫자로만('-' 제외) 11자 이내로 작성해주세요.)", groups = PatternCheckGroup.class)
    @NotBlank(message = "가게(휴대폰) 번호는 필수 입력항목입니다.", groups = NotBlanckGroup.class)
	private String shopTel;
	
	private String shopWebsite;
	
	private String promotionText;
	
	private Date registrationDate;
	
	private String longitude;
	
	private String latitude;
	
	private double rating;
	
	public Shop() {
		
	}
}