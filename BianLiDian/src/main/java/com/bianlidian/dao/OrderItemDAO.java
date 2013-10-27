package com.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedTypes;

import com.bianlidian.model.OrderItem;

@MappedTypes(OrderItem.class)
public interface OrderItemDAO {
	@Insert("insert into order_item(orderId,itemId,itemName,itemSku,itemNumber,itemPrice,"
			+ "createBy,createDate,updateBy,updateDate) values (#{orderId},#{itemId},#{itemName},#{itemSku},"
			+ "#{itemNumber},#{itemPrice},#{createBy},#{createDate},#{updateBy},#{updateDate})")
	public void save(OrderItem item);

	@Select("select * from order_item where orderId=#{orderId} order by id desc")
	public List<OrderItem> findAllItems(Integer orderId);
}
