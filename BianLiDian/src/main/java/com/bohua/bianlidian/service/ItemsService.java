package com.bohua.bianlidian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohua.bianlidian.dao.ItemsDAO;
import com.bohua.bianlidian.model.Items;
import com.bohua.bianlidian.model.ShoppingCart;

@Service
@Transactional(readOnly = true)
public class ItemsService {
	@Autowired
	private ItemsDAO itemsDAO;
	@Autowired
	private ShoppingCartService cartService;

	public List<Items> findAll(Integer categoryId, String openId) {
		List<ShoppingCart> carts = cartService.findAll(openId);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (ShoppingCart cart : carts) {
			map.put(cart.getItemId(), cart.getId());
		}
		List<Items> items = itemsDAO.findAll(categoryId);
		for (Items item : items) {
			item.setIsCarted(map.containsKey(item.getId()));
		}
		return items;
	}

	public Items find(Integer itemId) {
		return itemsDAO.getItems(itemId);
	}

	public List<Items> findByName(String name) {
		return itemsDAO.findByName(name);
	}
}
