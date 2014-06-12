package spring_basic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component // 用于注解自动加载，注释掉这行就取消掉这个Aspect的作用
@Aspect
public class LoggingAspect {

	/**
	 * 关于execution表达式：
	 * 1) 如果只写类名，那么所有包下类名相同的类都会发生作用。
	 * @param joinPoint
	 */
	@Before("execution(* CustomerBo.addCustomer(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}
	
}
