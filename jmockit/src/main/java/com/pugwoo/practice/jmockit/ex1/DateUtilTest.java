package com.pugwoo.practice.jmockit.ex1;

import java.util.Calendar;
import java.util.Date;

import mockit.Delegate;
import mockit.Expectations;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {
	
	// 获得一个日期，仅用于测试比较。
	public static Date getDate() {
		Calendar c = Calendar.getInstance();
		c.set(2010, 6, 22, 15, 52, 55);
		return c.getTime();
	}

	/**
	 * Mock某个类方法
	 */
	@Test
	public void testGetCurrentDateStr() {
		// DateUtil.class,要Mock的类
		new Expectations(DateUtil.class) {
			{
				/**
				 * 下面两句代码的含义：
				 * DateUtil的方法now()的返回值被mock为getDate()返回的结果：2010-07-22 15:52:55
				 */
				DateUtil.now(); // 要Mock的方法now,其他方法不改变行为
				result = getDate(); // 期望方法返回的结果
				
				/**
				 * 如果是多个方法和多个result，则一个方法一个result顺序写下来。
				 */
			}
		};
		
		/**
		 * 注意这里，mock【只修改一次】，如果我在这行代码执行先执行了：
		 * System.out.println(DateUtil.getCurrentDateStr());
		 * 那么上面这行打印的是2010-07-22 15:52:55
		 * 而下面这个调用返回的就是当前系统时间。
		 * 
		 * 如果需要执行多次，则使用times=xxx;来指定
		 */
		Assert.assertEquals("2010-07-22 15:52:55", DateUtil.getCurrentDateStr());
	}

	/**
	 * Mock 某个类方法根据不同参数返回不同值
	 */
	@Test
	public void testGetCurrentDateStrByFormatType() {
		new Expectations(DateUtil.class) {
			{
				DateUtil.getCurrentDateStrByFormatType(anyInt);
				result = new Delegate<DateUtil>() {
					@SuppressWarnings("unused")
					public String getCurrentDateStrByFormatType(int type) {
						if (type == 1) {
							return "2010/07/22 15:52:55";
						} else {
							return "2010-07-22 15:52:55";
						}
					}
				};
			}
		};
		
		Assert.assertEquals("2010-07-22 15:52:55",
				DateUtil.getCurrentDateStrByFormatType(2));
	}

}
