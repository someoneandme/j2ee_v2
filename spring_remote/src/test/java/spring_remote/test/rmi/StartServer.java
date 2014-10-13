package spring_remote.test.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试：启动RMI服务器，启动之后服务器会一直运行
 */
public class StartServer {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-rmi-server.xml");

		// 这样即可启动服务
		context.getBean("rmiService");

	}
}
