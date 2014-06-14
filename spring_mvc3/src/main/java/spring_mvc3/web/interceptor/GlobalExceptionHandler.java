package spring_mvc3.web.interceptor;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 2014年6月14日
 * 
 * 对全局生效的异常处理
 * 
 * 如果只需要某个controller的异常处理：
 * 那么只需要把@ExceptionHandler放在某个Controller中
 * 同时不需要@ControllerAdvice和@EnableWebMvc
 * 
 * 【重点】
 * 测试Test需要加上注解：@WebAppConfiguration
 */
@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {

	@ExceptionHandler(SQLException.class)
	public ModelAndView handlerSQLException(SQLException ex) {

		ModelAndView model = new ModelAndView("result");
		model.addObject("result", "非常抱歉，数据库异常，请稍候再试。详情：" + ex.getMessage());

		return model;
	}

}
