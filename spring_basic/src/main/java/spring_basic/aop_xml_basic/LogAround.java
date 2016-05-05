package spring_basic.aop_xml_basic;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 实际上，这种实现MethodInterceptor接口的方式也不错，比注解的方式要更容易接受
 * 
 * 
 */
public class LogAround implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        long start = System.currentTimeMillis();
		
		// 执行目标方法
        Object result = null;
        try {
        	result = invocation.proceed(); // 一定要调用
        } finally {
        	long end = System.currentTimeMillis();
        	System.out.println(className + "." + methodName + " execute:" +
        	    (end -start) + "ms.");
        }

		return result;
	}

}
