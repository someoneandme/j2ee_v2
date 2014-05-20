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
 * 2014年5月19日 16:40:23
 */
@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return "login";
	}
	
	@RequestMapping(value = "/logout")
	public String loginGet(HttpServletRequest req,
			Writer writer) throws IOException {
		req.getSession().setAttribute("login_user", null);
		return "logout";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void loginPost(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			HttpServletRequest req,
			HttpServletResponse resp,
			Writer writer) throws IOException{
		
		if (username == null || username.isEmpty() || password == null
				|| password.isEmpty()) {
			writer.write("{\"ret\":1,\"msg\":\"username and password required\"}");
			return;
		}
		
		boolean isLogin = userService.checkLogin(username, password);
		
		// 登录通过，在session中加入用户信息
		resp.setContentType("text/html;charset=utf-8");
		if(isLogin) {
			req.getSession().setAttribute("login_user", username);
			writer.write("登录成功,跳转到<a href=\"./\">首页</a>");
		} else {
			writer.write("登录失败, 重新<a href=\"./login\">登录</a>");
		}
	}
	
}
