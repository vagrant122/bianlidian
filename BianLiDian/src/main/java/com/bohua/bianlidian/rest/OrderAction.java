package com.bohua.bianlidian.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohua.bianlidian.model.Order;
import com.bohua.bianlidian.service.OrderService;

@Controller
@RequestMapping("rest/order")
public class OrderAction {
	@Autowired
	private OrderService orderService;
	private int pageSize = 10;

	@RequestMapping("/listOrders")
	public @ResponseBody
	List<Order> listOrders(int pageIndex, String status, Model model) {
		List<Order> orders = orderService.findAll(pageIndex, status, pageSize);

		return orders;
	}

	@RequestMapping("/saveOrder")
	public @ResponseBody
	String saveOrder(@RequestBody Order order, Model model) {
		orderService.saveReceiver(order);
		return "success";
	}

	@RequestMapping("/deliverOrder")
	public @ResponseBody
	String deliverOrder(@RequestBody Order order, Model model) {
		orderService.deliverOrder(order);
		return "success";
	}

	@RequestMapping("/closeOrder")
	public @ResponseBody
	String closeOrder(@RequestBody Order order, Model model) {
		orderService.closeOrder(order);
		return "success";
	}

	@RequestMapping("/undoOrder")
	public @ResponseBody
	String undoOrder(@RequestBody Order order, Model model) {
		orderService.undoOrder(order);
		return "success";
	}

	@RequestMapping("/handleOrder")
	public @ResponseBody
	String handleOrder(@RequestBody Order order, Model model) {
		orderService.handleOrder(order);
		return "success";
	}

	@RequestMapping("/hasNewOrder")
	public @ResponseBody
	Integer hasNewOrder(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date lastTime,
			String status, Model model) {
		return orderService.hasNewOrder(lastTime, status);
	}
}
