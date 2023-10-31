package hello.capstone.dto;



import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Shop {
	private int shopidx; 
	private String shopName;
	private int ownerIdx;
	private MultipartFile imageFile;
	private String imageFilename;
	private String shopAddress;
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