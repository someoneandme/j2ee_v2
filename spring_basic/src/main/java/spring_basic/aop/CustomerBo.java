package spring_basic.aop;

import java.sql.SQLException;

/**
 * 普通的接口，未入侵任何AOP的东西。
 * 
 * 这里演示了多种类型的接口方法：
 * 有返回值和无返回值的，有参数无参数的，抛异常不抛异常的。
 * 及其它们的组合。
 */
public interface CustomerBo {

	void addCustomer();

	String addCustomerReturnValue();

	void addCustomerThrowException() throws Exception;

	void addCustomerAround(String name);
	
	public String sayHello(String name) throws SQLException;
	
}
