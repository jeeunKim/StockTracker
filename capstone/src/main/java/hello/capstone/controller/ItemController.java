package hello.capstone.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hello.capstone.dto.Item;
import hello.capstone.dto.Member;
import hello.capstone.dto.Reservation;
import hello.capstone.dto.Shop;
import hello.capstone.exception.ValidationException;
import hello.capstone.service.ItemService;
import hello.capstone.service.ShopService;
import hello.capstone.validation.group.SaveItemValidationGroup;
import hello.capstone.validation.group.UpdateItemValidationGroup;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/item")
@RestController
@RequiredArgsConstructor
public class ItemController {

   private final ItemService itemService;
   private final ShopService shopService;
     
   /*
    * 아이템 등록
    */
//   @PostMapping("/register")
//   public Item ItemRegistration(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
//           				   @RequestParam(value = "itemidx", defaultValue = "0") String iidx,
//                           @RequestParam("shopidx") String sid,
//                           @RequestParam("itemName") String itemname,
//                           @RequestParam("cost") String ct,
//                           @RequestParam("salecost") String sct,
//                           @RequestParam("quantity") String qt,
//                           @RequestParam("category") String category,
//                           @RequestParam("itemnotice") String itemnotice,
//                           @RequestParam("endtime") String et,
//                           @RequestParam("starttime") String st,
//                           @RequestParam(value = "existingImage", required = false) String existingImage,
//                           @RequestParam(value = "method", defaultValue = "register") String method,
//                           HttpSession session
//                           ) throws IllegalStateException, IOException, ParseException {
//      
//      
//      
//      int itemidx = Integer.parseInt(iidx);
//      int shopidx = Integer.parseInt(sid);
//      int cost = Integer.parseInt(ct);
//      int salecost = Integer.parseInt(sct);
//      int quantity = Integer.parseInt(qt);
//      
//      Item item = new Item();
//      item.setItemidx(itemidx);
//      item.setShopidx(shopidx);
//      item.setItemname(itemname);
//      item.setItemnotice(itemnotice);
//      item.setCost(cost);
//      item.setSalecost(salecost);
//      item.setQuantity(quantity);
//      item.setCategory(category);
//
//
//      Timestamp starttime = convertStringToTimestamp(st);
//
//      Timestamp endtime = convertStringToTimestamp(et);
//      
//      item.setStarttime(starttime);
//      item.setEndtime(endtime);
//
//      if(imageFile != null) {
//         String fullPath = fileDir + imageFile.getOriginalFilename();
//         imageFile.transferTo(new File(fullPath));
//         item.setImage(imageFile.getOriginalFilename());
//      }
//      else {
//    	  item.setImage(existingImage);
//      }
//
//
//      
//      itemService.itemsave(item, method);
//        
//      return item;
//   }
   

   
   /*
    * item 등록
    */
   @PostMapping("/create")
   public void itemCreate(@Validated(value = SaveItemValidationGroup.class) @ModelAttribute Item item, BindingResult bindingResult,
                        @RequestParam("startParam") String startParam,
                        @RequestParam("endParam") String endParam) throws IllegalStateException, IOException, ParseException{
      
         if(bindingResult.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
          for (FieldError error : bindingResult.getFieldErrors()) {
               log.info("{} = {}", error.getField(), error.getDefaultMessage());
               errors.put(error.getField(), error.getDefaultMessage());
           }
          throw new ValidationException(errors);
      }
      
      item.setStarttime(convertStringToTimestamp(startParam));
      item.setEndtime(convertStringToTimestamp(endParam));
      
      itemService.saveItem(item);
   }
   
   /*
    * 아이템 수정
    */
   @PutMapping("/update")
   public void itemUpdate(@Validated(value = UpdateItemValidationGroup.class) @ModelAttribute Item item, BindingResult bindingResult, 
                        @RequestParam(value = "imageF", required = false) MultipartFile imageFile,
                       @RequestParam(value = "endParam", required = false) String endParam,
                       @RequestParam(value = "startParam", required = false) String startParam) 
                             throws ParseException, IllegalStateException, IOException {

         if(bindingResult.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
          for (FieldError error : bindingResult.getFieldErrors()) {
               log.info("{} = {}", error.getField(), error.getDefaultMessage());
               errors.put(error.getField(), error.getDefaultMessage());
           }
          throw new ValidationException(errors);
      }
      
      Item oldItem = itemService.findByItemIdx(item.getItemidx());

      oldItem.setItemname(item.getItemname());
      oldItem.setCost(item.getCost());
      oldItem.setSalecost(item.getSalecost());
      oldItem.setQuantity(item.getQuantity());
      oldItem.setCategory(item.getCategory());
      oldItem.setItemnotice(item.getItemnotice());
      log.info("endparam = {}", endParam);
      if(endParam != null || endParam =="" ) {
         oldItem.setEndtime(convertStringToTimestamp(endParam));
      }
      if(startParam != null || startParam =="" ) {
         oldItem.setStarttime(convertStringToTimestamp(startParam));
      }

      
      itemService.updateItem(oldItem, imageFile);
   }
   
   /*
    * 아이템 삭제
    */
   @DeleteMapping("/delete")
   public void itemDelete(@ModelAttribute Item item) {
	   itemService.itemDelete(item);
   }
   
   
   /*
    * 아이템 정보가져오기
    */
   @PostMapping("/getItems")
   public List<Item> getItems(@RequestBody Shop shop) {
      
      int shopIdx = shopService.getShopIdx(shop);
      
      List<Item> items = itemService.getItems(shopIdx);
      
      return items;
   }
   
    
   
   /*
    * 상품 예약
    */
   @PostMapping("/reservation")
   public void reservation(@RequestParam("shopidx") String si,
		   					 @RequestParam("memberidx") String mi,
		   					 @RequestParam("itemidx") String ii,
		   					 @RequestParam("number") String num,
		   					 @RequestParam("shopname") String shopname,
		   					 @RequestParam("itemname") String itemname,
		   					 @RequestParam("name") String name,
		   					 @RequestParam("phone") String phone) {
	   
	   int memberIdx = Integer.parseInt(mi);
	   int shopIdx = Integer.parseInt(si);
	   int itemIdx = Integer.parseInt(ii);
	   int number = Integer.parseInt(num);
	   
	   Reservation reservation = new Reservation(0,memberIdx,shopIdx,itemIdx,number,null,"wait");
	   
	   itemService.reservation(reservation, shopname, itemname, name, phone);
	   
   }
   
   /*
    *  상품 예약 확인(상업자가 확인 버튼 클릭)
    */
   @PostMapping("/reservation/confirm")
   public String confirm(@RequestParam("reservationidx") int reservationIdx) {
	   itemService.reservationConfirm(reservationIdx);
	   
	   return "";
   }
   
   /*
    * 상품 예약 취소(사용자)
    */
   @PostMapping("/reservation/cancel")
   public String cancel(HttpSession session, @RequestBody List<Map<String, Object>> reservationinfo) {
	   Member member = (Member) session.getAttribute("member");
	   String phone = member.getPhone();
	   String name = member.getName();
	   
	   log.info("phone = {}", phone);
	   log.info("name = {}", name);
	   
	   itemService.reservationCancel(reservationinfo, name, phone);
	   return "";
   }
   
   /*
    * 예약 상품 리스트 조회(사용자) (대기중인 예약, 완료된 예약 따로)
    */
   @GetMapping("/reservation/getreservations")
   public List<Map<String, Object>> getReservations(@RequestParam("confirm") String confirm,HttpSession session){
	   Member member = (Member) session.getAttribute("member");
	   return itemService.getReservations(member.getMemberIdx(), confirm);
   }
   
   /*
    * 상품 예약 취소(상업자)
    */
   @DeleteMapping("/reservation/cancel/business")
   public void reservationCancelBusiness(@RequestParam("reservationIdx") Integer reservationIdx) {
	   itemService.reservationCancelBusiness(reservationIdx);
	   
   }
   
   
   
   //---------------------------------------------------------------------------------------------------
   
   /*
    * String을 Timestamp로 변환하는 함수
    */
   private Timestamp convertStringToTimestamp(String dateString) throws ParseException {
     
	   try {
		   SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		   Date parsedDate = inputDateFormat.parse(dateString);
		   Timestamp parseTime = new Timestamp(parsedDate.getTime());
		   //타임존 반영을 위해 9시간 +
		   Timestamp Time = new Timestamp(parseTime.getTime() + (9 * 60 * 60 * 1000));
		   
		   return Time;
       
       } catch(ParseException e) {
    	   SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    	   Date parsedDate = inputDateFormat.parse(dateString);
    	   return new Timestamp(parsedDate.getTime());
       }
	       
   }
}







