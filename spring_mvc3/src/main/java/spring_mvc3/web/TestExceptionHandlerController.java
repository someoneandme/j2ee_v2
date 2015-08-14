package spring_mvc3.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2015年8月14日 20:40:41
 * 测试对当前controller的异常处理
 */
@Controller
public class TestExceptionHandlerController {

	/** 基于@ExceptionHandler异常处理
	 *  
	 *  注意：可以指定哪些异常由这个方法处理
	 */
	@ExceptionHandler({IOException.class})
	public ModelAndView exp(HttpServletRequest request, Exception ex) {
		// 也可以根据不同错误转向不同页面
		ModelAndView model = new ModelAndView("result");
		
		if(ex instanceof IOException) {
			model.addObject("result", "非常抱歉，数据库异常，请稍候再试。详情：" + ex.getMessage());
		} else {
			// 其它处理
		}
		return model;
	}
	
	@RequestMapping("/test_exception")
	public void test() throws Exception {
		throw new IOException("table not exists");
	}
	
	@RequestMapping("/test_runtime_exception")
	public void testRunTime() throws Exception {
		throw new RuntimeException("run time ex");
	}
}
