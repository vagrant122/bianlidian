package com.bianlidian.desk.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.client.RestTemplate;

import com.bianlidian.desk.util.ServerUrlUtils;
import com.bianlidian.model.Order;

public class OrderService {
	public static final String SUBMITTED = "已提交";
	public static final String CREATED = "未处理";
	public static final String HANDLED = "处理中";
	public static final String DILIVERED = "配送中";
	public static final String CLOSED = "已完成";

	public static List<Order> getNewOrders(int pageIndex, String status) {
		RestTemplate template = new RestTemplate();

		Order[] orders = template.getForObject(ServerUrlUtils.getOrderUrl(),
				Order[].class, pageIndex, status);

		return Arrays.asList(orders);
	}

	public static void saveOrder(Order order) {
		RestTemplate template = new RestTemplate();
		template.put(ServerUrlUtils.getSaveOrderUrl(), order);
	}

	public static void deliverOrder(Order order) {
		RestTemplate template = new RestTemplate();
		template.put(ServerUrlUtils.getDeliveryOrderUrl(), order);
	}

	public static void closeOrder(Order order) {
		RestTemplate template = new RestTemplate();
		template.put(ServerUrlUtils.getCloseOrderUrl(), order);
	}

	public static Integer hasNewOrder(Date lastTime, String status) {
		RestTemplate template = new RestTemplate();
		return template.getForObject(ServerUrlUtils.getNewOrderUrl(),
				Integer.class,
				DateFormatUtils.ISO_DATETIME_FORMAT.format(lastTime), status);
	}

	public static void handleOrder(Order order) {
		RestTemplate template = new RestTemplate();
		template.put(ServerUrlUtils.getHandleOrderUrl(), order);
	}

	public static void undoOrder(Order order) {
		RestTemplate template = new RestTemplate();
		template.put(ServerUrlUtils.getUndoOrderUrl(), order);
	}
}
