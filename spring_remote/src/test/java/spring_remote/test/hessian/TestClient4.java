package spring_remote.test.hessian;

import spring_remote.api.entity.UserDO;
import spring_remote.api.service.IUserService;

/**
 * 2015年1月8日 09:31:02
 */
public class TestClient4 {

	public static void main(String[] args) {
		IUserService userService = RemoteService.get(IUserService.class);
		
		System.out.println(userService.getInfo());
		System.out.println(userService.sayHello("nick"));
		
		UserDO user = userService.getUser();
		System.out.println("user.name:" + user.getName());
		System.out.println("user.score:" + user.getScore());
	}
	
}
