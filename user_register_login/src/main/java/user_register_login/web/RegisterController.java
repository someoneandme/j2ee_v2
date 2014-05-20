package user_register_login.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import user_register_login.service.UserService;

/**
 * 2014年5月20日 10:07:18
 */
@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGet() {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void registerPost(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "password2", required = false) String password2,
			HttpServletRequest req,
			HttpServletResponse resp,
			Writer writer) throws IOException{
		
		if (username == null || username.isEmpty() || password == null
				|| password.isEmpty()) {
			writer.write("{\"ret\":1,\"msg\":\"username and password required\"}");
			return;
		}
		
		if(password2 == null || !password.equals(password2)) {
			writer.write("{\"ret\":2,\"msg\":\"two password must be same\"}");
			return;
		}
		
		int ret = userService.addUser(username, password);
		
		// 登录通过，在session中加入用户信息
		resp.setContentType("text/html;charset=utf-8");
		if(ret == 0) {
			writer.write("注册成功,跳转到<a href=\"./\">首页</a>");
		} else {
			String errMsg;
			if(ret == 1) {
				errMsg = "用户名已存在";
			} else if(ret == 2) {
				errMsg = "用户名不能为空";
			} else {
				errMsg = "系统错误";
			}
			writer.write("注册失败, 原因:" + errMsg);
		}
	}
	
}
