package spring_basic.async.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_basic.async.SomeMethod;

/**
 * 2014年10月14日 21:07:40
 */
public class TestAysnc {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String xmlPath = "/applicationContext-async.xml";
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);

		SomeMethod someMethod = context.getBean(SomeMethod.class);

		System.out.println("before call somemethod");
		someMethod.sayHello(); // 异步，直接过
		System.out.println("after call somemethod");

		Future<String> result = someMethod.sayHello("nick");
        System.out.println("after call sayHello, result.isDone:" + result.isDone());		
		while(!result.isDone()) { // 等待结果返回
			System.out.println("waiting");
			System.out.println("result:" + result.get()); // result.get()会阻塞住，所以只打了一个waiting
		}
		
	}

}
