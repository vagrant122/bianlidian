package com.bohua.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.MappedTypes;

import com.bohua.bianlidian.model.ShoppingCart;

@MappedTypes(ShoppingCart.class)
public interface ShoppingCartDAO {
	@Insert("insert into shopping_cart(openId,itemId,itemName,itemSku,itemDescription,itemImg,itemNumber,itemPrice,"
			+ "createBy,createDate,updateBy,updateDate) values (#{openId},#{itemId},#{itemName},#{itemSku},"
			+ "#{itemDescription},#{itemImg},#{itemNumber},#{itemPrice},#{createBy},#{createDate},#{updateBy},#{updateDate})")
	public void save(ShoppingCart cart);

	@Update("update shopping_cart set itemNumber=#{itemNumber},itemPrice=#{itemPrice} where id=#{id}")
	public void update(ShoppingCart shopping);

	@Delete("delete from shopping_cart where id=#{id} and openId=#{openId}")
	public void remove(@Param("id") Integer id, @Param("openId") String openId);

	@Delete("delete from shopping_cart where itemId=#{itemId} and openId=#{openId}")
	public void removeByItem(@Param("itemId") Integer itemId,
			@Param("openId") String openId);

	@Select("select * from shopping_cart where openId=#{openId} and itemId=#{itemId}")
	public ShoppingCart find(@Param("itemId") Integer itemId,
			@Param("openId") String openId);

	@Select("select * from shopping_cart where openId=#{openId}")
	public List<ShoppingCart> findAll(String openId);

	@Select("select count(1) from shopping_cart where openId=#{openId}")
	public int count(String openId);

	@Delete("delete from shopping_cart where openId=#{openId}")
	public void removeAll(String openId);
}
