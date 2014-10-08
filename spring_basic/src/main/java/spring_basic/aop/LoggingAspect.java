package spring_basic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面对象，这个就是AOP的所有业务逻辑，AOP配置本身靠注解来标识的。
 * 切面对象本身也是只有业务逻辑，未继承或实现任何AOP的接口，非常清爽。
 * 
 * 注解@Component只是让这个类配置入Spring的世界，和xml配置等效，无AOP东西。
 */
@Component // 用于注解自动加载，注释掉这行就取消掉这个Aspect的作用
@Aspect
public class LoggingAspect {

	/**
	 * 前置通知
	 * 
	 * 关于execution表达式：
	 * 1) 如果只写类名，那么所有包下类名相同的类都会发生作用。
	 * 
	 * @param joinPoint
	 */
	@Before("execution(* CustomerBo.sayHello(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("logBefore() is running!");
		/**
		 * JoinPoint包含了所有需要的数据
		 */
		System.out.println("method : " + joinPoint.getSignature());
		System.out.print("args:[");
		for(Object obj : joinPoint.getArgs()) {
			System.out.print(obj + ",");
		}
		System.out.println("]");
		System.out.println("被拦截的对象:" + joinPoint.getThis());
		
		System.out.println("------------------");
	}
	
	// 此外还有的AOP拦截点：
	/**
	 * after 在方法执行之后执行
	 * around 环绕
	 * AfterReturning 返回后执行
	 * AfterThrowing 异常时执行
	 */
	
}
