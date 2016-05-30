package spring_remote.test.hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 2015年1月8日 09:19:19
 * 另外一种封装方式：不是使用new，这样可以做更多事情
 */
public class RemoteService {
	
	public static <T> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	/**
	 * 自行指定url
	 * 
	 * @param clazz
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz, String url) {
		
		// 这个url可以远程从配置中心拿 XXX
		if(clazz.getName() == "spring_remote.api.service.IUserService") {
			if(url == null) {
				url = "http://localhost:8080/spring_remote/remote/userService";
			}
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true); // 【重要】开启方法重载支持，不然接口里面重载的方法会调失败
			try {
				T service = (T) factory.create(clazz, url);
				return service;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
}
