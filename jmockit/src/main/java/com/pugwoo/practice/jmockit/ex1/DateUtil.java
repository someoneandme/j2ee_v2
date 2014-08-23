package com.pugwoo.practice.jmockit.ex1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2014年8月23日 15:23:01
 * 要被测试的代码,这里面没有入侵任何jmock的逻辑和代码
 */
public class DateUtil {

	private int type;

	/**
	 * 获得当前时间yyyy-MM-dd HH:mm:ss格式的字符串
	 * @return
	 */
	public static final String getCurrentDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(DateUtil.now());
	}

	/**
	 * 
	 * @param type 当type为1时，获得当前时间yyyy/MM/dd HH:mm:ss格式的字符串；
	 *             否则获得当前时间yyyy-MM-dd HH:mm:ss格式的字符串；
	 * @return
	 */
	public static final String getCurrentDateStrByFormatType(int type) {
		if (type == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format(DateUtil.now());
		} else {
			return DateUtil.getCurrentDateStr();
		}
	}

	public static final Date now() {
		return new Date();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
