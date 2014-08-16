package spring_basic.aop.test;

import java.sql.SQLException;

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
	public void testAOP() throws SQLException {
		System.out.println(customerBo);
		customerBo.sayHello("nick");
		
		// class com.sun.proxy.$Proxy11 可以拿出spring aop默认用jvm的动态代理
		// 如果是GCLib的话
		System.out.println("class:" + customerBo.getClass());
	}
	
}
