package spring_basic.aop;

import java.sql.SQLException;

public interface CustomerBo {

	void addCustomer();

	String addCustomerReturnValue();

	void addCustomerThrowException() throws Exception;

	void addCustomerAround(String name);
	
	public String sayHello(String name) throws SQLException;
	
}
