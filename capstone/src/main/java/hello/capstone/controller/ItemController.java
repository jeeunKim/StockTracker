package hello.capstone.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hello.capstone.dto.Item;
import hello.capstone.dto.Shop;
import hello.capstone.service.ItemService;
import hello.capstone.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/item")
@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	private final ShopService shopService;
	
	@Value("${itemfile.dir}")
	private String fileDir;
	
	/*
	 * 아이템 등록
	 */
	@PostMapping("/register")
	public String ItemRegistration(@RequestParam("image") MultipartFile Image,
								   @RequestParam("shopidx") int shopidx,
								   @RequestParam("itemname") String itemname,
								   @RequestParam("cost") int cost,
								   @RequestParam("salecost") int salecost,
								   @RequestParam("quantity") int quantity,
								   @RequestParam("category") String category,
								   @RequestParam("itemnotice") String itemnotice,
								   @RequestParam("endtime") LocalDateTime endtime
								   ) throws IllegalStateException, IOException {
		
		Item item = new Item();
		item.setShopidx(shopidx);
		item.setItemname(itemname);
		item.setItemNotice(itemnotice);
		item.setCost(cost);
		item.setSalecost(salecost);
		item.setQuantity(quantity);
		item.setCategory(category);

		LocalDateTime now = LocalDateTime.now();
		item.setStartTime(now);
		item.setEndTime(endtime);
		
		if(!Image.isEmpty()) {
			String fullPath = fileDir + Image.getOriginalFilename();
			Image.transferTo(new File(fullPath));
		}
		log.info("item = {}", item);
		item.setImage(Image.getOriginalFilename());
		
		itemService.itemsave(item);
		
		
		return "/owner";
	}
	
	/*
	 * 아이템 정보가져오기
	 */
	@PostMapping("/getItems")
	public List<Item> getItems(@RequestBody Shop shop) {
		int shopIdx = shopService.getShopIdx(shop);
		log.info("shop = {} ", shop);
		List<Item> items = itemService.getItems(shopIdx);
		
		log.info("items = {} ", items);
		return items;
	}
	
	/*
	 * 1분마다 실행되는 cron표현식 item들에 endtime을 확인하여 시간이 지나면 자동 삭제
	 */
	@Scheduled(cron ="0 0/1 * * * *")
	public void deleteItemEndtime() {
		LocalDateTime now = LocalDateTime.now();

        // 현재 시간보다 이전인 튜플 삭제
		itemService.deleteItemEndtime(now);
		log.info("삭제");
	}
}









