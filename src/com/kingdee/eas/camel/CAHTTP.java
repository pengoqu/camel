package com.kingdee.eas.camel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CAHTTP {
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 请求的 URL
	 * @param json
	 */
	public static String post(String url, String json) {
		/**统一使用utf-8编码*/
		String ENCODING_UTF_8 = "UTF-8";
		OutputStream out = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			//使用 URL 连接进行输入
			conn.setDoInput(true);
			//使用 URL 连接进行输出
			conn.setDoOutput(true);
			// 设置URL请求方法   
			conn.setRequestMethod("POST");
			// 忽略缓存 
			conn.setUseCaches(false);
			
			// 设置请求属性 
			byte[] bufferBytes = json.getBytes(ENCODING_UTF_8);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Charset", ENCODING_UTF_8);  
			conn.connect();
			
			// 建立输出流，并写入数据
			out = conn.getOutputStream();
			out.write(bufferBytes, 0, bufferBytes.length);
			out.close();
			
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				// 连接成功后，处理响应流
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String lines;
				while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), ENCODING_UTF_8);
	                sb.append(lines);
	            }
			}
			
			//断开连接
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！url=" + url);
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
