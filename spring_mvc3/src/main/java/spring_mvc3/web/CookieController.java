package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2014年3月20日 12:34:11
 * 测试spring mvc cookie写入和读取
 * 
 * http://127.0.0.1:8080/spring_mvc/test_cookie
 */
@Controller
public class CookieController {

	@RequestMapping(value = "/test_cookie")
	public String testCookie(Model model, Writer writer,
			HttpServletResponse resp,
			@CookieValue(value = "chinese2", required = false) String chinese2) // 通过注解获得cookie值
			throws IOException {
		
		System.out.println("recv cookie:" + chinese2); // 第二次访问：%C4%E3%BA%C3%D6%D0%CE%C4

		//Set-Cookie: foo=bar
		resp.addCookie(new Cookie("foo", "bar"));
		
		//Set-Cookie: chinese="`}-?
		resp.addCookie(new Cookie("chinese", "你好中文")); //【注意】： 如果cookie直接写中文，会丢失数据

		String encodeValue = URLEncoder.encode("你好中文", "gbk"); // %C4%E3%BA%C3%D6%D0%CE%C4
		System.out.println(encodeValue);
		Cookie cookie = new Cookie("chinese2", encodeValue);
		cookie.setMaxAge(60 * 60 * 24 *7); // 有效期7天
		// 还可以指定打在那个域下面setDomain
		
		// Set-Cookie: chinese2=%C4%E3%BA%C3%D6%D0%CE%C4; Expires=Thu, 27-Mar-2014 04:54:18 GMT
		resp.addCookie(cookie);

		writer.write("hello world");

		return null;
	}
}
