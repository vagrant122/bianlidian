package com.bianlidian.util;

import java.util.List;

import com.bianlidian.model.ShoppingCart;

public class PriceUtils {
	public static double totalPrice(List<ShoppingCart> cart) {
		double total = 0.0;

		for (ShoppingCart shopping : cart) {
			total += shopping.getItemPrice();
		}
		// 1元运费
		return total > 0 ? total + 1 : 0.0;
	}
}
