package com.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedTypes;

import com.bianlidian.model.Items;

@MappedTypes(Items.class)
public interface ItemsDAO {
	@Select("select * from Items where id=#{id}")
	public Items getItems(Integer id);

	@Insert("insert into Items(item_id, item_sku, item_name, create_by, create_date, update_by, update_date) values (#{id},#{sku},#{name},#{createBy},#{createDate},#{updateBy},#{updateDate})")
	public void saveItems(Items items);

	@Select("select * from Items where categoryId=#{categoryId}")
	public List<Items> findAll(Integer categoryId);

	@Select("select * from Items where name like CONCAT('%', #name#, '%')")
	public List<Items> findByName(String name);
}
