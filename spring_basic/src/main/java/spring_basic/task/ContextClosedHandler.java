package spring_basic.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * 2014年10月14日 20:50:35 这个类也不是必须的，但它可以在spring关闭时做些事情
 * 
 * 所以这个特性可以用于tomcat关闭时，做些清理操作
 */
@Component
class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
	
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	@Autowired
	ThreadPoolTaskScheduler scheduler;

	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println("==================Spring Context is shutting down==============");
		scheduler.shutdown();
		executor.shutdown();
	}
}