package spring_remote.test.hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

import spring_remote.api.entity.UserDO;
import spring_remote.api.service.IUserService;

/**
 * 2015年1月7日 15:51:55<br>
 * 演示一个可以直接new出来的远程服务，里面就可以做些配置中心的封装<br>
 * 
 * 这是一种组合的方式，但封装之后，对于使用者是非常友好的。
 */
public class RemoteUserService implements IUserService {

	private IUserService userService;

	public RemoteUserService() {
		// XXX 这个地址可以从配置中心拿
		String url = "http://localhost:8080/spring_remote/remote/userService";

		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			userService = (IUserService) factory
					.create(IUserService.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getInfo() {
		return userService.getInfo();
	}

	@Override
	public String sayHello(String name) {
		return userService.sayHello(name);
	}

	@Override
	public UserDO getUser() {
		return userService.getUser();
	}

}
