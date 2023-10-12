package hello.capstone.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
	
	private int itemidx;
	private int shopidx;
	private String itemname;
	private int cost;
	private int salecost;
	private int quantity;
	private String itemnotice;
	private String image;
	private String category;
	private Timestamp  starttime;
	private Timestamp  endtime;
	
	public Item() {}
	
}