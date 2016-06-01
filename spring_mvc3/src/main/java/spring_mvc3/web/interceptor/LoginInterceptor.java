package spring_mvc3.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 这里只是个示例
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	private static ThreadLocal<Long> userLoginContext = new ThreadLocal<Long>();
	
	/**
	 * 那当前登陆人的id
	 * @return
	 */
	public static Long getCurrentLoginUser() {
		return userLoginContext.get();
	}
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// 这里根据request里面的信息，查询缓存服务器或mysql，判断用户有没有登录
		// 有则userLoginContext.set(用户id)
		//    返回true
		// 没有则返回false
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// 重要，这里要清理掉用户登录信息，如果外围有Interceptor，那要放到最外围来做
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
