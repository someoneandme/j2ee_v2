package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 主要测试在servlet中获取spring容器
 */
@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {

	// 在初始化的时候也是可以的
//	private WebApplicationContext context;
//
//	@Override
//	public void init() throws ServletException {
//		super.init();
//		context = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(getServletContext());
//	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		System.out.println("context:" + context);
		
		resp.getWriter().write("hello,world");

	}

}
