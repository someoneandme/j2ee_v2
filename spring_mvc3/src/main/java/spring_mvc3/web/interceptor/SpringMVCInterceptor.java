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

	/**
	 * 请求处理前执行，返回true继续执行，返回false不再继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		System.out.println("SpringMVCInterceptor preHandle:" + req.getRequestURL());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("SpringMVCInterceptor postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object handler, Exception ex)
			throws Exception {
		System.out.println("SpringMVCInterceptor afterCompletion");
	}

}
