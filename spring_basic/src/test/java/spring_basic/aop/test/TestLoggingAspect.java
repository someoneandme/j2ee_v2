package spring_basic.aop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring_basic.aop.CustomerBo;

@ContextConfiguration(locations = "classpath:applicationContext-aop.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLoggingAspect {

	@Autowired
	private CustomerBo customerBo;
	
	@Test
	public void testBefore() {
		System.out.println(customerBo);
		customerBo.addCustomer();
	}
	
}
