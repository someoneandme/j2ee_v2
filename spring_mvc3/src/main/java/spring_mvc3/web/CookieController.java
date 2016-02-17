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
		
		//【报错】： 如果cookie直接写中文，会丢失数据或抛java.lang.reflect.InvocationTargetException
		// resp.addCookie(new Cookie("chinese", "你好中文")); 

		String encodeValue = URLEncoder.encode("你好中文", "utf-8"); // 【特别注意】这里的编码，要和页面的编码一致，这样传递过来才能被正确decode
		System.out.println(encodeValue);
		Cookie cookie = new Cookie("chinese2", encodeValue);
		cookie.setMaxAge(60 * 60 * 24 *7); // 有效期7天
		// 还可以指定打在那个域下面setDomain
		
		// Set-Cookie: chinese2=; Expires=Thu, 27-Mar-2014 04:54:18 GMT
		resp.addCookie(cookie);

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		writer.write("hello world, cookie chinese2=" + chinese2);
		return null;
	}
}
