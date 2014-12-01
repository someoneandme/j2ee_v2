package spring_mvc3.web.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2014年12月1日 14:49:08<br>
 * 自动将request中的参数设置到model中<br>
 * 只有model中没有重复的数据时，才会设置，也就是不会覆盖已有的model数据<br>
 * null值的request属性名称不会处理
 */
public class SyncRequestModelInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ModelMap model = modelAndView.getModelMap();
		Set<?> paramKey = request.getParameterMap().keySet();
		
		for(Object param : paramKey) {
			String p = param.toString();
			if(param != null && !model.containsAttribute(p)) {
				model.addAttribute(p, request.getParameter(p));
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
