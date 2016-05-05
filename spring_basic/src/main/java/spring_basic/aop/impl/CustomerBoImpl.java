package spring_basic.aop.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import spring_basic.aop.CustomerBo;

/**
 * 接口的实现，未入侵任何的AOP代码。
 * 注解@Component只是让spring加载入spring世界中，和xml配置效果等同，不代表入侵AOP.
 */
@Component
public class CustomerBoImpl implements Serializable, CustomerBo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addCustomer() {
		System.out.println("addCustomer() is running ");
	}

	public String addCustomerReturnValue() {
		System.out.println("addCustomerReturnValue() is running ");
		return "abc";
	}

	public void addCustomerThrowException() throws Exception {
		System.out.println("addCustomerThrowException() is running ");
		throw new Exception("Generic Error");
	}

	public void addCustomerAround(String name) {
		System.out.println("addCustomerAround() is running, args : " + name);
	}
	
	public String sayHello(String name) throws SQLException {
		System.out.println("hello " + name);
		return "hello" + name;
	}

}
