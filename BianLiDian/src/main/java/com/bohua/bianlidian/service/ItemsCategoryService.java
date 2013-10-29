package com.bohua.bianlidian.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohua.bianlidian.dao.ItemsCategoryDAO;
import com.bohua.bianlidian.model.ItemsCategory;

@Service
@Transactional(readOnly = true)
public class ItemsCategoryService {
	@Autowired
	private ItemsCategoryDAO itemsCategoryDAO;
	@Autowired
	private RabbitTemplate template;

	public List<ItemsCategory> findAll() {
		return itemsCategoryDAO.findAll();
	}

	@Transactional
	public void test() {
		template.convertAndSend("new order!!");
	}

}
