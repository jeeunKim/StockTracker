package hello.capstone.repository;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hello.capstone.dto.Shop;

@Mapper
public interface ShopMapper {

   Shop findByAddress(@Param("address") String address);  
   
   void saveShop(Shop shop);
  
   int getShopIdx(Shop shop);
   
   //shop mark 테스트용
   List<Shop> getShops();
   
   List<Shop> getShopByMember(@Param("memberidx") int memberidx);
   
   List<Shop> runPriceFilter(int price);
   
   void modifyShop(Shop shop);

   Shop getShopByIdx(@Param("shopidx") int shopidx);
   
   Shop getShopByItemIdx(@Param("itemidx") int itemidx);
   
   List<Shop> runRatingFilter(double rating);
   
   void setRatings(@Param("shopidx") int shopidx, @Param("rating") double rating);
   
   List<Map<String, Object>> getItemReservations(int itemidx);
}