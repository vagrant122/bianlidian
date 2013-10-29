package com.bohua.bianlidian.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtils;

public class ServiceUtils {
	public static void setCreateInfo(Object model, String openId)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.setProperty(model, "createBy", openId);
		BeanUtils.setProperty(model, "createDate", Calendar.getInstance()
				.getTime());
		BeanUtils.setProperty(model, "updateBy", openId);
		BeanUtils.setProperty(model, "updateDate", Calendar.getInstance()
				.getTime());
	}

	public static void setUpdateInfo(Object model, String openId)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.setProperty(model, "updateBy", openId);
		BeanUtils.setProperty(model, "updateDate", Calendar.getInstance()
				.getTime());
	}
}
