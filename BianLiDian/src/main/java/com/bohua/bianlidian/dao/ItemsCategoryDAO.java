package com.bohua.bianlidian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedTypes;

import com.bohua.bianlidian.model.ItemsCategory;

@MappedTypes(ItemsCategory.class)
public interface ItemsCategoryDAO {
	@Select("select * from items_category order by id")
	List<ItemsCategory> findAll();
}
