package spring_mvc3.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

/**
 * 2015年1月6日 14:00:57
 * 
 * 这个会被spring mvc interceptor拦截到
 * 
 * 使用@Component仅是让这个变成spring bean
 * value等价配置文件中的name，【name必须以/开头】
 * 
 * 等价于xml配置：
 * <bean name="/my_request" class="spring_mvc3.web.MyRequestHandler"/>
 * 
 * 2016年2月17日 20:04:40
 * 这种方式等价于@Controller
 */
@Component(value = "/my_request")
public class MyRequestHandler implements HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("hello, world, HttpRequestHandler");
	}

}
