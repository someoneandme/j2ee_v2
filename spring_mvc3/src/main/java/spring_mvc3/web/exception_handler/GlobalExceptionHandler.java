package spring_mvc3.web.exception_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2015年8月14日 21:15:34
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		// 也可以根据不同错误转向不同页面
		ModelAndView model = new ModelAndView("result");
		model.addObject("result", "详情：" + ex.getMessage());

		return model;
	}

}
