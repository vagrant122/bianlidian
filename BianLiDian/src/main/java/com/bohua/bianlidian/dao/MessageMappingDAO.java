package com.bohua.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedTypes;

import com.bohua.bianlidian.model.Items;
import com.bohua.bianlidian.model.MessageMapping;

@MappedTypes(Items.class)
public interface MessageMappingDAO {
	@Select("select * from message_mapping")
	public List<MessageMapping> findAll();
}
