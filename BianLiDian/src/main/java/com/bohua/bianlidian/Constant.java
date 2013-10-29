package com.bohua.bianlidian;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class Constant {
	private static Properties configuraion;
	public static final String COOKIE_OPEN_ID = "COOKIE_OPEN_ID";

	static {
		configuraion = new Properties();
		try {
			configuraion.load(new FileReader(ResourceUtils
					.getFile("classpath:bianli.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return configuraion.getProperty(key);
	}

	public static String getToken() {
		return configuraion.getProperty("token");
	}
}
