package spring_basic.aop.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_basic.aop.CustomerBo;

/**
 * 2014年10月8日 13:35:12
 */
public class TestMain {

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-aop.xml");

		CustomerBo customerBo = context.getBean(CustomerBo.class);
		// 已经是AOP后的代理对象 CustomerBoImpl$$EnhancerBySpringCGLIB$$4257eb95
		// class com.sun.proxy.$Proxy11 可以拿出spring aop默认用jvm的动态代理
		// 如果是GCLib的话，那就会显示Enhance$$
		// 这两种方式应该只有性能上的差别，业务上无差异。
		System.out.println(customerBo.getClass()); 

		Map<String, CustomerBo> map = context.getBeansOfType(CustomerBo.class);
		// 这里可以看到，尽管有AOP，但是该类型最终在spring容器中的对象只有一个，就是AOP处理后的那个
		System.out.println("customerBO num:" + map.size());
		System.out.println("============================");
		
		customerBo.sayHello("nick");
	}
}
