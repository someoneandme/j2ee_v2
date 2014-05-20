package user_register_login.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2014年5月19日 17:02:16
 */
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		String uri = req.getRequestURI();

		/**
		 * 注册、登录、登出不需要登陆态
		 */
		if (!uri.endsWith("/login") && !uri.endsWith("/logout")
				&& !uri.endsWith("/register")) {
			if (req.getSession().getAttribute("login_user") != null) {
				return true;
			} else {
				resp.sendRedirect("login");
				return false;
			}
		}
		return true;
	}

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

}
