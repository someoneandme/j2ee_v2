package spring_remote.service;

import spring_remote.api.entity.UserDO;
import spring_remote.api.service.IUserService;

/**
 * 2012年11月20日 16:59:09
 */
public class UserServiceImpl implements IUserService {

	public String getInfo() {
		return "This is spring remote service";
	}

	public String sayHello(String name) {
		return "hello: " + name;
	}

	public UserDO getUser() {
		UserDO user = new UserDO();
		user.setName("nick");
		user.setScore(99);

		return user;
	}

}
