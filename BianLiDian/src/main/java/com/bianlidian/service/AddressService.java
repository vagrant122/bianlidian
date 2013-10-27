package com.bianlidian.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bianlidian.dao.AddressDAO;
import com.bianlidian.model.Address;
import com.bianlidian.util.ServiceUtils;

@Service
@Transactional(readOnly = true)
public class AddressService {
	@Autowired
	private AddressDAO addressDAO;

	public List<Address> findAll(String openId) {
		return addressDAO.findAll(openId);
	}

	@Transactional
	public void create(Address address) throws IllegalAccessException,
			InvocationTargetException {
		ServiceUtils.setCreateInfo(address, address.getOpenId());
		addressDAO.save(address);
	}

	public Address find(Integer id) {
		return addressDAO.find(id);
	}
}
