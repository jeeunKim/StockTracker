package hello.capstone.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hello.capstone.dto.Item;

@Mapper
public interface ItemMapper {

	void saveitem(Item item);
	
	Item findByShopIdx_itemname(@Param("shopidx") int shopidx, @Param("itemname") String itemname);
	
	List<Item> getItems(@Param("shopidx") int shopidx);
	
	void deleteItemEndtime(@Param("time") String time);
	
	void pushAlarm(@Param("shopidx") int shopidx);
	
	void deleteTimeoutAlarm();
	
	List<HashMap<String, Integer>> getAlarm(@Param("memberidx") int memberidx);
}