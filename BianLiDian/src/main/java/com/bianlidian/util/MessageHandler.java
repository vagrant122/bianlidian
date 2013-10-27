package com.bianlidian.util;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.bianlidian.model.MessageMapping;
import com.bianlidian.service.MessageMappingService;

public class MessageHandler {
	@Autowired
	private MessageMappingService mappingService;

	private HashMap<String, MessageMapping> mapping;

	public String findMapping(String content) {
		if (mapping == null) {
			mapping = mappingService.findAll();

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mapping = mappingService.findAll();
				}
			}).start();
		}
		return mapping.get(content) == null ? null : mapping.get(content)
				.getResponseContent();
	}

	public Message handleRequest(String receiveMsg) throws DocumentException {
		Document document = DocumentHelper.parseText(receiveMsg);
		// 消息推送
		String msgType = document.getRootElement().elementText("MsgType");
		if (StringUtils.isNotBlank(msgType)) {
			Message message = new Message();
			Element rootElement = document.getRootElement();
			String toUser = rootElement.elementText("ToUserName");
			String fromUser = rootElement.elementText("FromUserName");

			message.setToUser(toUser);
			message.setFromUser(fromUser);
			message.setType(msgType);

			switch (msgType) {
			case "text":
				message.setContent(rootElement.elementText("Content"));
				break;
			case "event":
				message.setEvent(rootElement.elementText("Event"));
				message.setEventKey(rootElement.elementText("EventKey"));
				break;
			default:
				message = null;
			}

			return message;
		}
		return null;
	}

	public String getTemplate(Message message) {
		String template = "textMsg.ftl";

		switch (message.getType()) {
		case "text":
			template = "textMsg.ftl";
			break;
		case "event":
			template = "menuMsg.ftl";
			break;
		}

		return template;
	}
}
