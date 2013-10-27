package com.bianlidian;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

public class Menu {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String menu = FileUtils.readFileToString(
				ResourceUtils.getFile("classpath:menu.json"), "UTF-8");

		MediaType mediaType = new MediaType("application", "xml",
				Charset.forName("UTF-8"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);

		HttpEntity<String> entity = new HttpEntity<String>(menu, headers);

		RestTemplate template = new RestTemplate();

		String result = template
				.postForObject(
						"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
								+ "z1qkP4_0R8Mz07jfWRw2ADHq3bM22FZoAVQpuh1H0K8BGuA4hDcziUnkwvvl4y"
								+ "_8X7gBPyGkCQKUUHYogeris5nCkHGaKbK3kW2eYCRfOVzIf4srquQjR438P6X4tMNwDyGrDS5DsbuNUjjGQd8R9A",
						entity, String.class);
		System.out.println(result);
	}
}
