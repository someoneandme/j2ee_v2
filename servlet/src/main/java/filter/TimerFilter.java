package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 演示一个计时Filter
 */
public class TimerFilter implements Filter {

	/**
	 * 初始化
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("TimerFilter init");
	}

	/**
	 * 主要实现逻辑的地方
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		long start = System.currentTimeMillis();
		
		System.out.println("filter is called");
		
		// 这里可以读写request session response等信息
		HttpSession session = ((HttpServletRequest) request).getSession();
		System.out.println("in filter, session properties count:"
				+ session.getAttribute("count"));
		
		// 必须显式调用chain.doFilter触发下一个filter的调用
		chain.doFilter(request, response);
		
		long end = System.currentTimeMillis();
		System.out.println("req " + ((HttpServletRequest) request).getServletPath()
				+ " cost:" + (end - start) + " ms.");
		
		// 这里是filter执行完之后的代码
		System.out.println("filter is called over");
	}

	public void destroy() {
		System.out.println("TimerFilter destroy");
	}

}
