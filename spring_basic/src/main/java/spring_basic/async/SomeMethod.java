package spring_basic.async;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * 2014年10月14日 21:06:32
 */
@Component
public class SomeMethod {

	@Async
	public void sayHello() throws InterruptedException {
		System.out.println("will sleep 3s");
		Thread.sleep(3000);
		System.out.println("has sleep 3s");
	}
	
	@Async
	public Future<String> sayHello(String name) throws InterruptedException {
		String value = "hello," + name;
		Thread.sleep(6000);
		return new AsyncResult<String>(value);
	}
	
}
