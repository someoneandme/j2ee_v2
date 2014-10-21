package spring_basic.aop.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 2014年10月14日 20:26:49
 */
public class TestTask {

	public static void main(String[] args) throws InterruptedException {
		String xmlPath = "/applicationContext-task.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);

		System.out.println(context);
		
		// 拿到scheduler之后，可以控制一些任务的执行
		ThreadPoolTaskScheduler scheduler =
				(ThreadPoolTaskScheduler) context.getBean("taskScheduler");

        // 可以调用scheduler的execute方法动态新增一个task
		
		// 好像无法remove已经在执行的任务，也无法查看到当前在执行的任务
		
	}

}
