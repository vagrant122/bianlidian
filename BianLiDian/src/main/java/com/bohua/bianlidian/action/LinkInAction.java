package com.bohua.bianlidian.action;

import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.bohua.bianlidian.Constant;
import com.bohua.bianlidian.model.Order;
import com.bohua.bianlidian.service.OrderService;
import com.bohua.bianlidian.util.Message;
import com.bohua.bianlidian.util.MessageHandler;

import freemarker.template.Template;

@Controller
public class LinkInAction {
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	@Autowired
	private MessageHandler messageHandler;
	@Autowired
	private OrderService orderService;

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 微信接入，并验证接口
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping("linkIn")
	public @ResponseBody
	String linkIn(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String receiveMsg) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("New Message:" + receiveMsg);
		}
		// 网址接入
		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echostr)) {
			return validate(request);
		}

		Message message = messageHandler.handleRequest(receiveMsg);
		if (message != null) {
			String tempFile = messageHandler.getTemplate(message);
			Template template = freemarkerConfig.getConfiguration()
					.getTemplate(tempFile);
			StringWriter out = new StringWriter();

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("toUser", message.getFromUser());
			root.put("fromUser", message.getToUser());
			root.put("createTime", String.valueOf(System.currentTimeMillis()));

			switch (message.getType()) {
			case "text":
				Order order = processOrder(message);
				if (order != null) {
					root.put("textType", "orderNumber");
					root.put("order", order);
					template.process(root, out);
				}
				break;
			case "event":
				root.put("event", message.getEvent());
				root.put("eventKey", message.getEventKey());
				template.process(root, out);
				break;
			default:
			}

			return out.toString();
		}

		return "";
	}

	private Order processOrder(Message message) {
		if (StringUtils.isNotBlank(message.getType())) {

			String content = message.getContent();
			if (content != null && content.length() == 2
					&& NumberUtils.isNumber(content)) {
				Order order = orderService.confirmOrder(content);
				return order;
			} else {
				orderService.updateDelivery(content, message.getFromUser());
			}

		}
		return null;
	}

	public String validate(HttpServletRequest request)
			throws NoSuchAlgorithmException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		boolean result = validate(signature, timestamp, nonce,
				Constant.getToken());

		if (log.isDebugEnabled()) {
			log.debug("signature:" + signature + ",timestamp:" + timestamp
					+ ",nonce:" + nonce + ",echostr:" + echostr + " result:"
					+ result);
		}
		if (result) {
			return echostr;
		} else {
			return "failed";
		}
	}

	private boolean validate(String signature, String timestamp, String nonce,
			String token) throws NoSuchAlgorithmException {
		String[] params = { timestamp, nonce, token };
		Arrays.sort(params);

		MessageDigest instance = MessageDigest.getInstance("SHA-1");
		instance.update((params[0] + params[1] + params[2]).getBytes());

		String hexString = new BigInteger(1, instance.digest()).toString(16);
		if (signature.equals(hexString)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		LinkInAction action = new LinkInAction();
		String nonce = "1374854642";
		String timestamp = "1375253089";
		String signature = "07f0244972d38c83ea80d768e9d713e1c819f498";
		boolean result = action.validate(signature, timestamp, nonce,
				"bianlidian");

		System.out.println(result);
	}
}
