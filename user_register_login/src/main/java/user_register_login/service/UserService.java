package user_register_login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import user_register_login.entity.UserInfo;

/**
 * 2014年5月19日 16:32:03 为了方便起见，这里就不用接口了
 */
@Service
public class UserService {

	private static List<UserInfo> users = new ArrayList<UserInfo>();

	static {
		UserInfo user = new UserInfo();
		user.setUsername("admin");
		user.setPassword("admin");

		users.add(user);
	}

	/**
	 * 校验用户登陆态
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String username, String password) {
		for (UserInfo user : users) {
			if (user.getUsername().equals(username)
					&& user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 注册用户，返回值0表示注册成功，返回1表示用户已存在，2表示用户名为空
	 * @param username
	 * @param password
	 * @return
	 */
	public int addUser(String username, String password) {
		
		if(username == null || username.isEmpty()) {
			return 2;
		}
		
		for (UserInfo user : users) {
			if(user.getUsername().equals(username))
				return 1;
		}
		
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		
		users.add(user);
		return 0;
	}

}
