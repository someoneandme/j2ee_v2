package com.pugwoo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拿系统定义的参数值
 * @author nick
 */
public class Configs {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Configs.class);

	private static Properties prop = new Properties();
	
	static {
		String fileName = "/redis.properties";

		InputStream in = Configs.class.getResourceAsStream(fileName);
		if(in != null) {
			try {
				prop.load(in);
			} catch (IOException e) {
				LOGGER.error("prop load exception", e);
			}
		} else {
			LOGGER.error("cannot find properties:{}", fileName);
		}
	}
	
	/**
	 * 拿系统配置，会自动根据环境来拿；配置文件在base-${env}.properties文件中
	 * @param key
	 * @return null if not found
	 */
	public static String getConfig(String key) {
		return prop.getProperty(key);
	}

}
