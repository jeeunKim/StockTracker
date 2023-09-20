package hello.capstone.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hello.capstone.dto.Member;
import hello.capstone.dto.Shop;
import hello.capstone.service.MemberService;
import hello.capstone.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {
	
	private final MemberService memberService;
	private final ShopService shopService;
	
	@Value("${file.dir}")
	private String fileDir;
	
	@PostMapping("/shopRegistration")
	public String shopRegistration(@RequestBody Shop shop, @RequestBody MultipartFile Image,HttpSession session) throws IllegalStateException, IOException {
		
		Member ownerMember = (Member)session.getAttribute("member");
		int ownerIdx = memberService.getMeberIdx(ownerMember);
		
		shop.setOwnerIdx(ownerIdx);
		
		if(!Image.isEmpty()) {
			String fullPath = fileDir + Image.getOriginalFilename();
			log.info("파일 저장 fullPath ={}",fullPath);
			Image.transferTo(new File(fullPath));
		}
		shop.setImageFilename(Image.getOriginalFilename());
		shopService.saveShop(shop);
		
		
		return "/home_user";
	}
}




















