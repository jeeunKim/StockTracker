package hello.capstone.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {

	private int shopidx;
	private String itemname;
	private int cost;
	private int salecost;
	private int quantity;
	private String itemNotice;
	private String image;
	private String category;
	private LocalDateTime  startTime;
	private LocalDateTime  endTime;
	
	public Item() {}
	
}