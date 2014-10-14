package spring_basic.task;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 2014年10月14日 20:29:20
 */
@Component
public class MyTask {

	/**
	 * fixedRate 固定每隔一秒执行一次
	 * fixedDelay 固定间隔一秒执行一次
	 */
	@Scheduled(fixedRate = 1000)
	public void sayHello() {
		System.out.println("===hello===" + new Date());
	}
	
	/**
	 * cron表达式:
	 * 秒 分 时 月份中的日期 月 星期中的日期 年
	 */
	@Scheduled(cron = "0/2 * * * * *")
	public void sayHello2() {
		System.out.println("===hello2===" + new Date());
	}
	
}
