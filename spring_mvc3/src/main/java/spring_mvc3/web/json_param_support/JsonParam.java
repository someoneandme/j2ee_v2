package spring_mvc3.web.json_param_support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * http://zjumty.iteye.com/blog/1927890
 * 些许改造 2015年8月15日 12:13:05
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

	/**
	 * request变量名
	 */
	String value() default "";
	
}
