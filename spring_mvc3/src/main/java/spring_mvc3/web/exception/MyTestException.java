package spring_mvc3.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 2015年8月16日 22:53:03
 * 加入注解@ResponseStatus后
 * 如果方法抛出这个异常，就会被spring mvc转换成http错误码和信息
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "测试错误原因")
public class MyTestException extends Exception {

	private static final long serialVersionUID = 1L;

}
