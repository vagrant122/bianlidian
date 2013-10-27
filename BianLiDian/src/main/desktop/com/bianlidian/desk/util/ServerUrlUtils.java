package com.bianlidian.desk.util;

public class ServerUrlUtils {
	private static String host = "http://bianlidian.guangshenbian.com/BianLiDian/rest";

	// private static String host = "http://localhost:85/BianLiDian/rest";

	public static String getOrderUrl() {
		return host + "/order/listOrders?pageIndex={pageIndex}&status={status}";
	}

	public static String getSaveOrderUrl() {
		return host + "/order/saveOrder";
	}

	public static String getDeliveryOrderUrl() {
		return host + "/order/deliverOrder";
	}

	public static String getCloseOrderUrl() {
		return host + "/order/closeOrder";
	}

	public static String getNewOrderUrl() {
		return host + "/order/hasNewOrder?lastTime={lastTime}&status={status}";
	}

	public static String getHandleOrderUrl() {
		return host + "/order/handleOrder";
	}

	public static String getUndoOrderUrl() {
		return host + "/order/undoOrder";
	}
}
