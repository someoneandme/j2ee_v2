package spring_basic.aop.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_basic.aop.CustomerBo;

/**
 * 2014年10月8日 13:35:12
 */
public class TestMain {

	public static void main(String[] args) {
		String xmlPath = "/applicationContext-aop.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);

		System.out.println(context);

		CustomerBo customerBo = context.getBean(CustomerBo.class);
		System.out.println(customerBo);
		System.out.println(customerBo.getClass()); // 已经是AOP后的代理对象

		Map<String, CustomerBo> map = context.getBeansOfType(CustomerBo.class);
		System.out.println(map);
		// 这里可以看到，尽管有AOP，但是该类型最终在spring容器中的对象只有一个，就是AOP处理后的那个
		System.out.println("customerBO num:" + map.size());
	}
}
