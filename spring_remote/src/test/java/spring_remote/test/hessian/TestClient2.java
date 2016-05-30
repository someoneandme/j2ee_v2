package spring_remote.test.hessian;

import java.net.MalformedURLException;

import spring_remote.api.entity.UserDO;
import spring_remote.api.service.IUserService;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 2015年1月7日 15:40:12
 * 测试直接new对象，不通过spring xml配置来
 */
public class TestClient2 {

	public static void main(String[] args) throws MalformedURLException {
		String url = "http://localhost:8080/spring_remote/remote/userService";
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); // 【重要】开启方法重载支持，不然接口里面重载的方法会调失败
		
		IUserService userService = (IUserService) factory.create(IUserService.class, url);
	
		System.out.println(userService.getInfo());
		System.out.println(userService.sayHello("nick"));
		
		UserDO user = userService.getUser();
		System.out.println("user.name:" + user.getName());
		System.out.println("user.score:" + user.getScore());
	}
	
}
