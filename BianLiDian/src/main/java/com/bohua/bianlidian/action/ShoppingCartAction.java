package com.bohua.bianlidian.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohua.bianlidian.model.Address;
import com.bohua.bianlidian.model.Items;
import com.bohua.bianlidian.model.ShoppingCart;
import com.bohua.bianlidian.service.AddressService;
import com.bohua.bianlidian.service.ItemsService;
import com.bohua.bianlidian.service.ShoppingCartService;
import com.bohua.bianlidian.util.PriceUtils;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartAction {
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private ShoppingCartService shoppingService;
	@Autowired
	private AddressService addressService;

	@RequestMapping
	public String index(String openId, Model model)
			throws IllegalAccessException, InvocationTargetException {
		List<ShoppingCart> cart = shoppingService.findAll(openId);

		model.addAttribute("totalPrice", PriceUtils.totalPrice(cart));
		model.addAttribute("shoppingcart", cart);
		model.addAttribute("openId", openId);

		return "shoppingcart";
	}

	@RequestMapping("increase/{itemId}")
	public @ResponseBody
	Result increase(@PathVariable Integer itemId, String openId, Model model)
			throws IllegalAccessException, InvocationTargetException {
		Items item = itemsService.find(itemId);
		ShoppingCart cart = shoppingService.increaseItem(openId, item);
		List<ShoppingCart> carts = shoppingService.findAll(openId);

		Result result = new Result();

		result.setTotalPrice(PriceUtils.totalPrice(carts));
		result.setItemNumber(cart.getItemNumber());
		result.setItemPrice(cart.getItemPrice());

		return result;
	}

	@RequestMapping("decrease/{itemId}")
	public @ResponseBody
	Result decrease(@PathVariable Integer itemId, String openId, Model model)
			throws IllegalAccessException, InvocationTargetException {
		Items item = itemsService.find(itemId);
		ShoppingCart cart = shoppingService.decreaseItem(openId, item);
		List<ShoppingCart> carts = shoppingService.findAll(openId);

		Result result = new Result();

		result.setTotalPrice(PriceUtils.totalPrice(carts));
		result.setItemNumber(cart.getItemNumber());
		result.setItemPrice(cart.getItemPrice());

		return result;
	}

	class Result {
		private Double itemPrice;
		private Integer itemNumber;
		private Double totalPrice;

		public Double getItemPrice() {
			return itemPrice;
		}

		public void setItemPrice(Double itemPrice) {
			this.itemPrice = itemPrice;
		}

		public Integer getItemNumber() {
			return itemNumber;
		}

		public void setItemNumber(Integer itemNumber) {
			this.itemNumber = itemNumber;
		}

		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}
	}

	/**
	 * 
	 * @param id
	 *            item id or shopping cart id
	 * @param openId
	 * @param model
	 */
	@RequestMapping("delete/{id}")
	public @ResponseBody
	Double delete(@PathVariable Integer id, String openId, Model model) {
		shoppingService.deleteItem(id, openId);
		List<ShoppingCart> carts = shoppingService.findAll(openId);

		return PriceUtils.totalPrice(carts);
	}

	@RequestMapping("submit")
	public @ResponseBody
	int submitOrder(String openId, Address address, Model model)
			throws IllegalAccessException, InvocationTargetException {
		int orderNumber = shoppingService.submit(openId, address);
		return orderNumber;
	}

	@RequestMapping("address")
	public String submitOrder(String openId, Model model) {
		List<ShoppingCart> cart = shoppingService.findAll(openId);

		model.addAttribute("totalPrice", PriceUtils.totalPrice(cart));
		model.addAttribute("addressList", addressService.findAll(openId));
		model.addAttribute("openId", openId);

		return "shoppingaddress";
	}
}
