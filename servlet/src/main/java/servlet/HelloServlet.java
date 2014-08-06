package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 只要是实现Servlet的接口都可以。
 * 但是实现servlet接口需要实现其所有的接口，而继承则不需要，有默认实现。
 */
public class HelloServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 通过这里可以看到：HelloServlet只实例化一个对象
	 */
	public HelloServlet() {
		super();
		System.out.println("HelloServlet instanced");
	}

	/**
	 * 初始化servlet
	 */
	@Override
	public void init() throws ServletException {
		System.out.println("HelloServlet init");
		
		// 支持获得init参数
		String a = getInitParameter("a");
		String b = getInitParameter("b");
		System.out.println("init param, a=" + a + ",b=" + b);
	}

	/**
	 * 重写doGet方法对Http Get方法响应
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Integer count = (Integer) session.getAttribute("count");
		if(count == null) {
			count = 1;
		} else {
			count++;// 并发情况下会有问题，不过问题不大，可以考虑用AtomicInteger
		}
		session.setAttribute("count", count);
		
		resp.setContentType("text/plain; charset=utf-8");
		resp.getWriter().write("hello world, 你好, 第" + count +
				"次访问");
		
		System.out.println("HelloServlet.doGet is called");
		
	}
	
}
