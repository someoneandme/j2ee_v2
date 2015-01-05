package spring_remote.test.hessian;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_remote.api.entity.UserDO;
import spring_remote.api.service.IUserService;

/**
 * 2014年10月13日 17:37:17
 */
public class TestClient {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-hessian-client.xml");

		// 获取到远程的remote对象
		IUserService userService = (IUserService) context
				.getBean("userService");

		/**
		 * 实际发送的请求数据request.getInputStream():
		 * 63 02 00 6D 00 07 67 65   c? m ?ge
		 * 74 49 6E 66 6F 7A         tInfoz
		 */
		System.out.println(userService.getInfo());

		System.out.println(userService.sayHello("nick"));

		// 测试获取一个自定义的结构
		UserDO user = userService.getUser();
		System.out.println("user.name:" + user.getName());
		System.out.println("user.score:" + user.getScore());
		
	}

}
