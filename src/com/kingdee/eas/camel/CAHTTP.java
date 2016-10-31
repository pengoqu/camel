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
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url ����� URL
	 * @param json
	 */
	public static String post(String url, String json) {
		/**ͳһʹ��utf-8����*/
		String ENCODING_UTF_8 = "UTF-8";
		OutputStream out = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			//ʹ�� URL ���ӽ�������
			conn.setDoInput(true);
			//ʹ�� URL ���ӽ������
			conn.setDoOutput(true);
			// ����URL���󷽷�   
			conn.setRequestMethod("POST");
			// ���Ի��� 
			conn.setUseCaches(false);
			
			// ������������ 
			byte[] bufferBytes = json.getBytes(ENCODING_UTF_8);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Charset", ENCODING_UTF_8);  
			conn.connect();
			
			// �������������д������
			out = conn.getOutputStream();
			out.write(bufferBytes, 0, bufferBytes.length);
			out.close();
			
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				// ���ӳɹ��󣬴�����Ӧ��
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String lines;
				while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), ENCODING_UTF_8);
	                sb.append(lines);
	            }
			}
			
			//�Ͽ�����
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��url=" + url);
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
