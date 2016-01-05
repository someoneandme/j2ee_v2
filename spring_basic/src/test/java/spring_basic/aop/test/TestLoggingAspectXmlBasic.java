package spring_basic.aop.test;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring_basic.aop.CustomerBo;

/**
 * 这两个注解@ContextConfiguration和@RunWith是典型的Spring测试类注解，
 * 用于启动测试时启动spring容器，和AOP无特定关系。
 * 
 */
@ContextConfiguration(locations = "classpath:applicationContext-aop_xml_basic.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLoggingAspectXmlBasic {

	/**
	 * 【重要】需要指定customerBoProxy
	 */
	@Autowired
	@Qualifier("customerBoProxy")
	private CustomerBo customerBo;
	
	@Test
	public void testAOP() throws SQLException {
		System.out.println(customerBo);
		customerBo.sayHello("nick");
		
		// class com.sun.proxy.$Proxy11 可以拿出spring aop默认用jvm的动态代理
		// 如果是GCLib的话，那就会显示Enhance$$
		// 这两种方式应该只有性能上的差别，业务上无差异。
		System.out.println("class:" + customerBo.getClass());
	}
	
}
