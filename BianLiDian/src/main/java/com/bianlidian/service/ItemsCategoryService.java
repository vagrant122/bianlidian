package com.bianlidian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bianlidian.dao.ItemsCategoryDAO;
import com.bianlidian.model.ItemsCategory;

@Service
@Transactional(readOnly = true)
public class ItemsCategoryService {
	@Autowired
	private ItemsCategoryDAO itemsCategoryDAO;

	public List<ItemsCategory> findAll() {
		return itemsCategoryDAO.findAll();
	}
	
}
