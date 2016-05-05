package spring_basic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
	
	// 可以定义一个可复用的切点，其它@Before之类的注解就写pointcut()
	@Pointcut("execution(* CustomerBo.sayHello(..))")
	public void pointcut() {
	}

	/**
	 * 前置通知
	 * 
	 * 关于execution表达式：
	 * 1) 如果只写类名，那么所有包下类名相同的类都会发生作用。
	 * 2) 左边的*表示匹配任何类型的返回值
	 * 3) 参数中..表示匹配任何的参数
	 * @param joinPoint 这些参数也是注入的，不需要时不写也可以；但是不能写其它的参数
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
		
		// @Before无法控制被拦截的方法不要往下执行，得用@Around
		System.out.println("--------logBefore end----------");
	}
	
	@After("execution(* CustomerBo.*(..))")
	public void logAfter() {
		System.out.println("--------logAfter called--------");
	}
	
	@Around("execution(* CustomerBo.*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("--------around before--------");
        pjp.proceed();
        System.out.println("--------around after--------");
    }
	
	// 此外还有的AOP拦截点：
	/**
	 * after 在方法执行之后执行
	 * around 环绕
	 * AfterReturning 返回后执行
	 * AfterThrowing 异常时执行
	 */
	
	// 关于连接点表达式 2014年10月10日 23:41:20
	/**
	 * 常用的还有：
	 * && within(包名.*) 指定只处理指定包下面的类
	 * && bean(spring中bean名称) 指定spring中指定的bean，!bean()也常用
	 * 
	 */
}
