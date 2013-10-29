package com.bohua.bianlidian.action;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohua.bianlidian.model.Items;
import com.bohua.bianlidian.model.ShoppingCart;
import com.bohua.bianlidian.service.ItemsService;
import com.bohua.bianlidian.service.ShoppingCartService;

@Controller
public class ListAction {
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private ShoppingCartService shoppingService;

	@RequestMapping("list/{categoryId}")
	public String list(@PathVariable Integer categoryId, String openId,
			Model model) throws DocumentException {
		List<Items> items = itemsService.findAll(categoryId, openId);

		model.addAttribute("items", items);
		model.addAttribute("cartNum", shoppingService.number(openId));
		model.addAttribute("openId", openId);

		return "list";
	}

	@RequestMapping("list/check")
	public @ResponseBody
	List<Result> check(String openId, Model model) {
		List<ShoppingCart> carts = shoppingService.findAll(openId);

		List<Result> result = new ArrayList<Result>();
		for (ShoppingCart cart : carts) {
			result.add(new Result(cart.getItemId()));
		}

		return result;
	}

	class Result {
		private Integer itemId;

		Result(Integer id) {
			itemId = id;
		}

		public Integer getItemId() {
			return itemId;
		}

		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}
	}
}
