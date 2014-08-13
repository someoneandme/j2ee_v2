package com.pugwoo.practice.spring_schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSchema {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext-test.xml");
		
		People p = (People) ctx.getBean("cutesource");
		System.out.println(p.getId());
		System.out.println(p.getName());
		System.out.println(p.getAge());

	}

}
