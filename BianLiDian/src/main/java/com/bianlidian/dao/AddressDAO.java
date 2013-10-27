package com.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedTypes;

import com.bianlidian.model.Address;

@MappedTypes(Address.class)
public interface AddressDAO {
	@Select("select * from address where openId=#{openId} order by id")
	List<Address> findAll(String openId);

	@Insert("insert into address(openId,address,telephone,receiver,"
			+ "createBy,createDate,updateBy,updateDate) values "
			+ "(#{openId},#{address},#{telephone},#{receiver},"
			+ "#{createBy},#{createDate},#{updateBy},#{updateDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Address address);

	@Select("select * from address where id=#{id}")
	Address find(Integer id);
}
