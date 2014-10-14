package spring_basic.aop.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 2014年10月14日 20:26:49
 */
public class TestTask {

	public static void main(String[] args) throws InterruptedException {
		String xmlPath = "/applicationContext-task.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);

		System.out.println(context);
		
	}

}
