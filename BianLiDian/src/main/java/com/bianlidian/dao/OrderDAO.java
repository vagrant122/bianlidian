package com.bianlidian.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.MappedTypes;

import com.bianlidian.model.Order;

@MappedTypes(Order.class)
public interface OrderDAO {
	@Insert("insert into orders(openId,address,telephone,receiver,status,orderNumber,totalPrice,"
			+ "createBy,createDate,updateBy,updateDate) values (#{openId},#{address},"
			+ "#{telephone},#{receiver},#{status},#{orderNumber},#{totalPrice},"
			+ "#{createBy},#{createDate},#{updateBy},#{updateDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void save(Order order);

	@Update("update orders set receiver=#{receiver},address=#{address},telephone=#{telephone},updateDate=#{updateDate} where id=#{id}")
	public void saveReceiver(Order order);

	@Update("update orders set status=#{status},updateDate=#{updateDate} where id=#{id}")
	public void saveStatus(Order order);

	@Select("select * from orders where status=#{status} order by id desc limit #{index},#{pageSize}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "items", column = "id", javaType = List.class, many = @Many(select = "com.bianlidian.dao.OrderItemDAO.findAllItems")) })
	public List<Order> findAll(@Param("index") int index,
			@Param("pageSize") int pageSize, @Param("status") String status);

	@Select("select count(1) from orders where updateDate>=#{lastTime} and status=#{status}")
	public Integer hasNewOrder(@Param("lastTime") Date lastTime,
			@Param("status") String status);

	@Select("select * from orders where status='已提交' and orderNumber=#{orderNum} order by id desc limit 0,1")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "items", column = "id", javaType = List.class, many = @Many(select = "com.bianlidian.dao.OrderItemDAO.findAllItems")) })
	public Order findSubmittedOrder(@Param("orderNum") String orderNum);

	@Update("update orders set delivery=#{delivery},updateDate=#{updateDate} where (delivery is null or delivery='') and status!='已提交' and openId=#{openId}")
	public void updateDelivery(Order order);
}
