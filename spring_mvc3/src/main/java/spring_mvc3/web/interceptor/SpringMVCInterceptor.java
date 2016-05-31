package spring_mvc3.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2013年5月24日 07:53:30
 * 拦截器演示
 */
public class SpringMVCInterceptor implements HandlerInterceptor {
	
	// 要计算每个请求的总时间，可以用ThreadLocal来做，也可以把变量放到request中来做

	/**
	 * 请求处理前(目标方法被调用前)执行，返回true继续执行，返回false不再继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		System.out.println("SpringMVCInterceptor preHandle:" + req.getRequestURL());
		return true;
	}

	/**
	 * 调用目标方法之后，渲染视图之前被调用
	 * 
	 * 只有preHandle 返回true才会执行到这里
	 */
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("SpringMVCInterceptor postHandle");
	}

	/**
	 * 渲染视图之后被调用
	 * 
	 * 只有preHandle 返回true才会执行到这里
	 */
	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object handler, Exception ex)
			throws Exception {
		System.out.println("SpringMVCInterceptor afterCompletion");
	}

}
