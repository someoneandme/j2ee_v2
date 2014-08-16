package spring_basic.aop.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import spring_basic.aop.CustomerBo;

@Component
public class CustomerBoImpl implements CustomerBo {

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
