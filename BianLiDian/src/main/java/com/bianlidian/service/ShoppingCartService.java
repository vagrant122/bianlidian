package com.bianlidian.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.catalina.Session;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bianlidian.dao.OrderDAO;
import com.bianlidian.dao.OrderItemDAO;
import com.bianlidian.dao.ShoppingCartDAO;
import com.bianlidian.model.Address;
import com.bianlidian.model.Items;
import com.bianlidian.model.Order;
import com.bianlidian.model.OrderItem;
import com.bianlidian.model.ShoppingCart;
import com.bianlidian.util.Message;
import com.bianlidian.util.PriceUtils;
import com.bianlidian.util.ServiceUtils;

@Service
@Transactional(readOnly = true)
public class ShoppingCartService {
	@Autowired
	private ShoppingCartDAO shoppingDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderItemDAO orderItemDAO;
	@Autowired
	private AddressService addressService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQQueue queueDestination;

	public List<ShoppingCart> findAll(String openId) {
		return shoppingDAO.findAll(openId);
	}

	@Transactional
	public ShoppingCart increaseItem(String openId, Items item)
			throws IllegalAccessException, InvocationTargetException {
		ShoppingCart shopping = find(openId, item);

		if (shopping != null) {// 购物车已存在商品
			shopping.setItemNumber(shopping.getItemNumber() + 1);
			shopping.setItemPrice(multiply(item.getPrice(),
					shopping.getItemNumber()));

			ServiceUtils.setUpdateInfo(shopping, openId);

			shoppingDAO.update(shopping);
		} else {// 购物车不存在该商品
			shopping = new ShoppingCart();

			shopping.setItemId(item.getId());
			shopping.setItemSku(item.getSku());
			shopping.setItemName(item.getName());
			shopping.setItemDescription(item.getDescription());
			shopping.setItemImg(item.getImgUrl());
			shopping.setItemNumber(1);
			shopping.setItemPrice(multiply(item.getPrice(),
					shopping.getItemNumber()));

			shopping.setOpenId(openId);

			ServiceUtils.setCreateInfo(shopping, openId);

			shoppingDAO.save(shopping);
		}
		return shopping;
	}

	private double multiply(double price, int number) {
		return Precision.round(price * number, 2);
	}

	@Transactional
	public ShoppingCart decreaseItem(String openId, Items item)
			throws IllegalAccessException, InvocationTargetException {
		ShoppingCart shopping = find(openId, item);

		if (shopping != null) {// 购物车存在商品
			if (shopping.getItemNumber() > 1) { // 减少商品数量，最小减到1，不能删除商品
				shopping.setItemNumber(shopping.getItemNumber() - 1);
				shopping.setItemPrice(multiply(item.getPrice(),
						shopping.getItemNumber()));

				ServiceUtils.setUpdateInfo(shopping, openId);

				shoppingDAO.update(shopping);
			}
		}

		return shopping;
	}

	@Transactional
	public void deleteItem(Integer id, String openId) {
		shoppingDAO.remove(id, openId);
		shoppingDAO.removeByItem(id, openId);
	}

	@Transactional
	public void deleteAllItem(String openId) {
		shoppingDAO.removeAll(openId);
	}

	public ShoppingCart find(String openId, Items item) {
		return shoppingDAO.find(item.getId(), openId);
	}

	public int number(String openId) {
		return shoppingDAO.count(openId);
	}

	@Transactional
	public int submit(String openId, Address address)
			throws IllegalAccessException, InvocationTargetException {
		if (address.getReceiver() != null) {
			addressService.create(address);
		} else {
			address = addressService.find(address.getId());
		}

		List<ShoppingCart> items = findAll(openId);

		RandomDataGenerator generator = new RandomDataGenerator();
		int orderNumber = generator.nextInt(10, 99);
		Order order = new Order();
		order.setOrderNumber(orderNumber);
		order.setOpenId(openId);
		order.setTotalPrice(PriceUtils.totalPrice(items));
		order.setStatus(OrderService.CREATED);
		ServiceUtils.setCreateInfo(order, openId);
		if (address.getReceiver() != null) {
			order.setAddress(address.getAddress());
			order.setReceiver(address.getReceiver());
			order.setTelephone(address.getTelephone());
		}
		orderDAO.save(order);

		for (ShoppingCart shopping : items) {
			OrderItem item = new OrderItem();
			item.setOrderId(order.getId());
			item.setItemId(shopping.getItemId());
			item.setItemName(shopping.getItemName());
			item.setItemNumber(shopping.getItemNumber());
			item.setItemPrice(shopping.getItemPrice());
			item.setItemSku(shopping.getItemSku());
			ServiceUtils.setCreateInfo(item, openId);
			orderItemDAO.save(item);
		}
		deleteAllItem(openId);

		jmsTemplate.send(queueDestination, new MessageCreator() {
			public Message createMessage(Session session) {
				return session.createTextMessage(message);
			}
		});

		return orderNumber;
	}
}
