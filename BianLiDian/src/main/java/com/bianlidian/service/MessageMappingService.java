package com.bianlidian.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bianlidian.dao.MessageMappingDAO;
import com.bianlidian.model.MessageMapping;

@Service
@Transactional(readOnly = true)
public class MessageMappingService {
	@Autowired
	private MessageMappingDAO mappingDAO;

	public HashMap<String, MessageMapping> findAll() {
		List<MessageMapping> list = mappingDAO.findAll();

		HashMap<String, MessageMapping> map = new HashMap<String, MessageMapping>();
		for (MessageMapping mm : list) {
			map.put(mm.getMsgContent(), mm);
		}

		return map;
	}
}
