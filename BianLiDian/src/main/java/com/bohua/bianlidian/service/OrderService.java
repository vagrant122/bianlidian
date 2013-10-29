package com.bohua.bianlidian.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohua.bianlidian.dao.OrderDAO;
import com.bohua.bianlidian.model.Order;

@Service
@Transactional(readOnly = true)
public class OrderService {
	public static final String SUBMITTED = "已提交";
	public static final String CREATED = "未处理";
	public static final String HANDLED = "处理中";
	public static final String DILIVERED = "配送中";
	public static final String CLOSED = "已完成";

	@Autowired
	private OrderDAO orderDAO;

	public List<Order> findAll(int pageIndex, String status, int pageSize) {
		return orderDAO.findAll(pageIndex * pageSize, pageSize, status);
	}

	@Transactional
	public void saveReceiver(Order order) {
		order.setUpdateDate(Calendar.getInstance().getTime());
		orderDAO.saveStatus(order);
	}

	@Transactional
	public Order confirmOrder(String orderNum) {
		Order order = orderDAO.findSubmittedOrder(orderNum);

		if (order != null) {
			order.setStatus(CREATED);
			order.setUpdateDate(Calendar.getInstance().getTime());
			orderDAO.saveStatus(order);
		}
		return order;
	}

	@Transactional
	public void deliverOrder(Order order) {
		order.setStatus(DILIVERED);
		order.setUpdateDate(Calendar.getInstance().getTime());
		orderDAO.saveStatus(order);
	}

	@Transactional
	public void closeOrder(Order order) {
		order.setStatus(CLOSED);
		order.setUpdateDate(Calendar.getInstance().getTime());
		orderDAO.saveStatus(order);
	}

	public Integer hasNewOrder(Date lastTime, String status) {
		return orderDAO.hasNewOrder(lastTime, status);
	}

	@Transactional
	public void updateDelivery(String content, String openId) {
		Order order = new Order();
		order.setDelivery(content);
		order.setOpenId(openId);
		order.setUpdateDate(Calendar.getInstance().getTime());

		orderDAO.updateDelivery(order);
	}

	@Transactional
	public void handleOrder(Order order) {
		order.setStatus(HANDLED);
		order.setUpdateDate(Calendar.getInstance().getTime());
		orderDAO.saveStatus(order);
	}

	@Transactional
	public void undoOrder(Order order) {
		order.setStatus(CREATED);
		order.setUpdateDate(Calendar.getInstance().getTime());
		orderDAO.saveStatus(order);
	}
}
