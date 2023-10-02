package hello.capstone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import hello.capstone.dto.Item;
import hello.capstone.exception.SaveItemException;
import hello.capstone.exception.SaveShopException;
import hello.capstone.exception.TimeSettingException;
import hello.capstone.exception.errorcode.ErrorCode;
import hello.capstone.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {

	private final ItemRepository itemRepository;
	
	/*
	 * 아이템 등록
	 */
	public boolean itemsave(Item item) {
		
		Optional.ofNullable(itemRepository.findByShopIdx_itemname(item.getShopidx(), item.getItemname()))
			.ifPresent(user->{
				throw new SaveItemException(ErrorCode.DUPLICATED_ITEM,null);
			});
		
		LocalDateTime startTime = item.getStartTime();
		LocalDateTime endtime = item.getEndTime();
		
		int timeOut = startTime.compareTo(endtime);
		if(timeOut >= 0) {
			throw new TimeSettingException(ErrorCode.TIME_SETTING_ERROR,null);
		}
		
		itemRepository.saveitem(item);
		
		return true;
	}
	
	/*
	 * 아이템 가져오기
	 */
	
	public List<Item> getItems(int shopIdx){
		List<Item> items = new ArrayList<Item>();
		items = itemRepository.getItems(shopIdx);
		
		return items;
		
	}
	
	
	public void deleteItemEndtime(LocalDateTime now) {

        // 현재 시간보다 이전인 튜플 삭제
		itemRepository.deleteItemEndtime(now);
	}
	
	
}