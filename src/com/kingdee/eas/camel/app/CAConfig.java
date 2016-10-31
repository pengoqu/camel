package com.kingdee.eas.camel.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CAConfig {
	public static final String CONFIG_FILE_PATH = "/server/properties/ca.properties";
	private static Properties mailConfig = null;
	public static CAConfig instance = null;
	
	private CAConfig() {
		mailConfig = new Properties();
	    String easHome = System.getProperty("EAS_HOME");
	    InputStream in = null;
	    
	    try {
			in = new BufferedInputStream(new FileInputStream(easHome + CONFIG_FILE_PATH));
			mailConfig.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				//ignore
			}
		}
	}
	
	public static CAConfig getInstance() {
		if (null == instance) {
			instance = new CAConfig();
		}
		return instance;
	}
	
	public String getValue(String key) {
		String value = mailConfig.getProperty(key);
		return value;
	}
}
