package com.bianlidian.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class PostMenu {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String json = FileUtils
				.readFileToString(
						FileUtils
								.getFile("D:/work/workspace_self/BianLiDian/src/main/resources/menu.json"),
						"UTF-8");

		createMenu(
				json,
				"wf1kNgIuyYPi2SZYguA4kDHz1Vr3eUe-qpx0PTMnEhDL9qOCun7jHkrQ0eMsFiU4Bwi7rMgqFg8_WbwBoSq_LrKzRz_Fu35qsIPUKgpxRSWsa4dsJANqk1e_GyF4CGmK8zChdTNE_tabIqJSJ6I9bQ");
	}

	public static void createMenu(String params, String accessToken) {

		StringBuffer bufferRes = new StringBuffer();

		try {

			URL realUrl = new URL(
					"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
							+ accessToken);

			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();

			// 连接超时

			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢，增大时间

			conn.setReadTimeout(25000);

			HttpURLConnection.setFollowRedirects(true);

			// 请求方式

			conn.setRequestMethod("GET");

			conn.setDoOutput(true);

			conn.setDoInput(true);

			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

			conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");

			conn.connect();

			// 获取URLConnection对象对应的输出流

			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());

			// 发送请求参数

			// out.write(URLEncoder.encode(params,"UTF-8"));

			out.write(params);

			out.flush();

			out.close();

			InputStream in = conn.getInputStream();

			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));

			String valueString = null;

			while ((valueString = read.readLine()) != null) {

				bufferRes.append(valueString);

			}

			System.out.println(bufferRes.toString());

			in.close();

			if (conn != null) {

				// 关闭连接

				conn.disconnect();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
